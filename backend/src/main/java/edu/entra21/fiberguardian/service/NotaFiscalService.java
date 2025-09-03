package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.NotaFiscalNaoEncontradaException;
import edu.entra21.fiberguardian.model.*;
import edu.entra21.fiberguardian.repository.ItemNotaFiscalRepository;
import edu.entra21.fiberguardian.repository.LaboratorioRepository;
import edu.entra21.fiberguardian.repository.NotaFiscalRepository;
import edu.entra21.fiberguardian.repository.PdfNotaFiscalRepository;
import edu.entra21.fiberguardian.service.storage.MultiPartFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.InputStream;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true) //  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario

public class NotaFiscalService {

    private final NotaFiscalRepository notaFiscalRepository;
    private final FornecedorService fornecedorService;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final PdfNotaFiscalRepository pdfNotaFiscalRepository;
    private final MultiPartFileStorageService notaFiscalStorageService;
    private final LaboratorioRepository laboratorioRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalService.class);

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository,
                             FornecedorService fornecedorService,
                             ItemNotaFiscalRepository itemNotaFiscalRepository,
                             UsuarioService usuarioService,
                             ProdutoService produtoService,
                             PdfNotaFiscalRepository pdfNotaFiscalRepository,
                             MultiPartFileStorageService notaFiscalStorageService,
                             PdfNotalFiscalService pdfNotalFiscalService,
                             LaboratorioRepository laboratorioRepository
    ) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.fornecedorService = fornecedorService;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
        this.pdfNotaFiscalRepository = pdfNotaFiscalRepository;
        this.notaFiscalStorageService = notaFiscalStorageService;
        this.laboratorioRepository = laboratorioRepository;
    }


    @Transactional(readOnly = false)
    public NotaFiscal salvarNotaComItensEPdf(NotaFiscal nota,
                                             List<ItemNotaFiscal> itens,
                                             PdfNotaFiscal pdf,
                                             InputStream arquivoStream) {

        // Valida duplicidade + associa fornecedor e usuário
        validaSeNFNaoEstaDuplicadaParaFornecedor(nota.getFornecedor().getCnpj(),
                nota.getCodigoNf());
        Fornecedor fornecedor = fornecedorService.buscarPorCNPJObrigatorio(nota.getFornecedor().getCnpj());
        Usuario usuario = usuarioService.buscarPorEmailObrigatorio(nota.getRecebidoPor().getEmail());
        nota.setFornecedor(fornecedor);
        nota.setRecebidoPor(usuario);

        // Associa itens
        itens.forEach(item -> {
            Produto produto = produtoService.buscarPorCnpjECodigoObrigatorio(
                    fornecedor.getCnpj(), item.getProduto().getCodigo());
            item.setProduto(produto);
            item.setNotaFiscal(nota);
            item.setValorTotalItem(
                    item.getPrecoUnitario()
                            .multiply(item.getQtdRecebida())
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        });
        nota.setItens(itens);

        // Salva nota fiscal e itens
        NotaFiscal notaSalva = notaFiscalRepository.save(nota);

        // Gera o nome hasheado ANTES de salvar os metadados
        String nomeOriginal = pdf.getNomeArquivo();
        String novoNomeArquivo = notaFiscalStorageService.gerarNomeArquivo(nomeOriginal);


        // Salva metadados do PDF (sem ainda gravar arquivo físico)
        pdf.setNotaFiscal(notaSalva);
        pdf.setNomeArquivo(novoNomeArquivo); // Agora salva com hash no banco
        PdfNotaFiscal pdfSalvo = pdfNotaFiscalRepository.save(pdf);

        // Captura o nome final ANTES do callback (evita nova geração)
        final String nomeArquivoFinal = pdfSalvo.getNomeArquivo();

        // Apenas após o commit, grava o arquivo no storage
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //String novoNomeArquivo = notaFiscalStorageService.gerarNomeArquivo(pdfSalvo.getNomeArquivo());

                MultiPartFileStorageService.NovoMultipartFile novoArquivo =
                        MultiPartFileStorageService.NovoMultipartFile.builder()
                                .nomeArquivo(nomeArquivoFinal)
                                .inputStream(arquivoStream)
                                .contentType(pdfSalvo.getContentType())
                                .build();

                notaFiscalStorageService.substituir(null, novoArquivo); // substitui se existir
            }
        });

        return notaSalva;
    }

     /**
     * Autocomplete por código parcial da nota fiscal.
     * Retorna no máximo 20 resultados.
     */
    public List<NotaFiscal> buscarPorCodigoNf(String codigoParcialNf) {
        if (codigoParcialNf == null || codigoParcialNf.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByCodigoNfContainingIgnoreCase(codigoParcialNf);
    }

    /**
     * Autocomplete por nome parcial do fornecedor.
     * Retorna no máximo 20 resultados, evitando N+1 no fornecedor.
     */
//    public List<NotaFiscal> buscarPorNomeFornecedor(String nomeParcialFornecedor) {
//        if (nomeParcialFornecedor == null || nomeParcialFornecedor.isBlank()) {
//            return Collections.emptyList();
//        }
//        return notaFiscalRepository.findTop20ByFornecedorNomeFornecedorContainingIgnoreCase(nomeParcialFornecedor);
//    }

    /**
     * Autocomplete por nome parcial do usuário que recebeu a nota.
     * Retorna no máximo 20 resultados, evitando N+1 no usuário.
     */
    public List<NotaFiscal> buscarPorNomeUsuario(String nomeParcialUsuario) {
        if (nomeParcialUsuario == null || nomeParcialUsuario.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByRecebidoPorNomeContainingIgnoreCase(nomeParcialUsuario);
    }

    public List<NotaFiscal> buscarPorFornecedorECodigoNfParcial(String cnpjFornecedor, String codigoNf) {

        //logger.warn("[FG] CNPJ Fornecedor : " + cnpjFornecedor);
        //logger.warn("[FG] Código Parcial Nota Fiscal : " + codigoNf);

        if (cnpjFornecedor == null || cnpjFornecedor.isBlank()) {
            return Collections.emptyList();
        }

        // Normaliza o parâmetro para evitar NullPointerException
        String codigoNormalizado = (codigoNf == null ? "" : codigoNf.trim());

        return notaFiscalRepository.findTop20ByFornecedor_CnpjAndCodigoNfContainingIgnoreCaseOrderByCodigoNfAsc(
                cnpjFornecedor.trim(),
                codigoNormalizado
        );
    }

    public NotaFiscal buscarOuFalhar(Long pdfNotaFiscalId ) {

        return  notaFiscalRepository.findNotaFiscalById(pdfNotaFiscalId)
                .orElseThrow(() -> new NotaFiscalNaoEncontradaException(pdfNotaFiscalId));
    }

    private void validaSeNFNaoEstaDuplicadaParaFornecedor(String cnpj, String notaFiscal) {
        if (notaFiscalRepository.existsByFornecedorCnpjAndCodigoNf(cnpj.trim(), notaFiscal.trim())) {
            throw new NegocioException(
                    String.format("Nota Fiscal '%s' para o fornecedor de CNPJ '%s' já existe no banco de dados.",
                            notaFiscal.trim(), cnpj.trim())
            );
        }
    }

    public NotaFiscal buscarObrigatorioPorNotaECnpj(String cnpj, String notaFiscal){
        return notaFiscalRepository
                .findByFornecedorCnpjAndCodigoNf(cnpj.trim(), notaFiscal.trim())
                .orElseThrow(() -> new NotaFiscalNaoEncontradaException(cnpj,notaFiscal));
    }

    /**
     * Exclui uma nota fiscal pelo CNPJ do fornecedor e código da nota.
     * @param cnpj CNPJ do fornecedor
     * @param codigoNF Código da nota fiscal
     * @throws NegocioException se a nota não existir
     */

    /*
    @Transactional(readOnly = false)
    public void excluirNotaFiscal(String cnpj, String codigoNF) {
        // Busca a nota fiscal
        NotaFiscal notaFiscal = notaFiscalRepository
                .findByFornecedorCnpjAndCodigoNf(cnpj.trim(), codigoNF.trim())
                .orElseThrow(() -> new NegocioException(
                        "Nota fiscal " + codigoNF + " do fornecedor " + cnpj + " não existe."
                ));

        // Verifica registros associados no laboratório
        long nrRegistros = laboratorioRepository.countByNotaFiscalId(notaFiscal.getId());
        if (nrRegistros > 0) {
            throw new NegocioException(
                    "Existem " + nrRegistros + " registros associados à nota fiscal "
                            + codigoNF + ", exclua esses registros antes de deletar a NF."
            );
        }

        // Captura o metadados do PDF antes de excluir
        PdfNotaFiscal pdf = pdfNotaFiscalRepository.findById(notaFiscal.getId())
                .orElse(null);

        // Exclui a nota fiscal -> cascade vai apagar itens e a FK do PDF
        logger.info("[FG] Deletando nota fiscal.....");
        notaFiscalRepository.delete(notaFiscal);
        notaFiscalRepository.flush(); // força o Hibernate a executar o DELETE

        // dentro de excluirNotaFiscal
        if (pdf != null) {
            // deleta no banco dentro da transação
            pdfNotaFiscalRepository.delete(pdf);

            // registra a exclusão do arquivo físico
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    notaFiscalStorageService.remover(pdf.getNomeArquivo());
                }
            });
        }
    }
    */

    @Transactional(readOnly = false)
    public void excluirNotaFiscal(String cnpj, String codigoNF) {

        NotaFiscal notaFiscal = buscarObrigatorioPorNotaECnpj(cnpj,codigoNF);

        // Verifica registros associados no laboratório
        long nrRegistros = laboratorioRepository.countByNotaFiscalId(notaFiscal.getId());
        if (nrRegistros > 0) {
            throw new NegocioException(
                    "Existem " + nrRegistros + " registros associados à nota fiscal "
                            + codigoNF + ", exclua esses registros antes de deletar a NF."
            );
        }

        // Captura dados do PDF antes da exclusão
        String nomeArquivo = null;
        if (notaFiscal.getPdfNotaFiscal() != null) {
            nomeArquivo = notaFiscal.getPdfNotaFiscal().getNomeArquivo();
        }
        //logger.info("[FG] Nome do arquivo.....: "+nomeArquivo);


        // Exclui a nota fiscal (cascade deleta o PDF automaticamente)
        //logger.info("[FG] Deletando nota fiscal.....");
        notaFiscalRepository.delete(notaFiscal);
        notaFiscalRepository.flush();

        // Registra a exclusão do arquivo físico
        if (nomeArquivo != null) {
            final String arquivo = nomeArquivo;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    notaFiscalStorageService.remover(arquivo);
                }
            });
        }
    }

}

