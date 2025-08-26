package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.NotaFiscalNaoEncontradaException;
import edu.entra21.fiberguardian.model.*;
import edu.entra21.fiberguardian.repository.ItemNotaFiscalRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalService.class);

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository,
                             FornecedorService fornecedorService,
                             ItemNotaFiscalRepository itemNotaFiscalRepository,
                             UsuarioService usuarioService,
                             ProdutoService produtoService,
                             PdfNotaFiscalRepository pdfNotaFiscalRepository, MultiPartFileStorageService notaFiscalStorageService
    ) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.fornecedorService = fornecedorService;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
        this.pdfNotaFiscalRepository = pdfNotaFiscalRepository;
        this.notaFiscalStorageService = notaFiscalStorageService;
    }


    @Transactional
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

        // Apenas após o commit, grava o arquivo no storage
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                String novoNomeArquivo = notaFiscalStorageService.gerarNomeArquivo(pdfSalvo.getNomeArquivo());

                MultiPartFileStorageService.NovoMultipartFile novoArquivo =
                        MultiPartFileStorageService.NovoMultipartFile.builder()
                                .nomeArquivo(novoNomeArquivo)
                                .inputStream(arquivoStream)
                                .contentType(pdfSalvo.getContentType())
                                .build();

                notaFiscalStorageService.substituir(null, novoArquivo); // substitui se existir
            }
        });

        return notaSalva;
    }

    /*

        @Transactional
        public NotaFiscal salvarComItens(NotaFiscal nota, List<ItemNotaFiscal> itens) {

            validaSeNFNaoEstaDuplicadaParaFornecedor(nota.getFornecedor().getCnpj(),
                    nota.getCodigoNf());
            Fornecedor fornecedor= fornecedorService.buscarPorCNPJObrigatorio(nota.getFornecedor().getCnpj());
            Usuario usuario = usuarioService.buscarPorEmailObrigatorio(nota.getRecebidoPor().getEmail());

            nota.setFornecedor(fornecedor);
            nota.setRecebidoPor(usuario);

            NotaFiscal finalNota = nota;

            itens.forEach(item -> {
                Produto produto = produtoService.buscarPorCnpjECodigoObrigatorio(
                        fornecedor.getCnpj(),
                        item.getProduto().getCodigo()
                );

                item.setProduto(produto);

                item.setNotaFiscal(finalNota);

                BigDecimal valorTotalItem = item.getPrecoUnitario()
                        .multiply(item.getQtdRecebida())
                        .setScale(2, RoundingMode.HALF_EVEN);

                item.setValorTotalItem(valorTotalItem); // se você tiver um campo para armazenar

            });

            nota.setItens(itens);

            NotaFiscal notaSaved = notaFiscalRepository.save(nota);

            // registra ação a ser executada só depois do commit
             TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    // aqui eu devo salvar os metadados da nota fiscal e o pdf da nota fiscal


                }
            });

            return nota;
        }
    */


    /*
    @Transactional
    public NotaFiscal salvar(NotaFiscalComItensInput notaFiscalComItensInput) {
        // valida
        // se existe fornecedor
        // se existe usuario
        // se nota para aquele fornecedor já existe.
        // validacoes do json campo vazio, numero negativo etc...ja estão implementadas na classe
        // NotaFiscalInput e excessoes manipuladas pelo handler de excecoes.

        validaSeNFNaoEstaDuplicadaParaFornecedor(notaFiscal.getFornecedor().getCnpj(),
                                                 notaFiscal.getCodigoNf());
        Fornecedor fornecedor= fornecedorService.buscarPorCNPJObrigatorio(notaFiscal.getFornecedor().getCnpj());
        Usuario usuario = usuarioService.buscarPorEmailObrigatorio(notaFiscal.getRecebidoPor().getEmail());

        notaFiscal.setFornecedor(fornecedor);
        notaFiscal.setRecebidoPor(usuario);
        return notaFiscalRepository.save(notaFiscal);
    }
    */

    @Transactional
    public NotaFiscal atualizar(String cnpj, String codigoNf, NotaFiscal notaFiscalAlterada) {
        // Regra de negócio específica será implementada depois
        return null;
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
    public List<NotaFiscal> buscarPorNomeFornecedor(String nomeParcialFornecedor) {
        if (nomeParcialFornecedor == null || nomeParcialFornecedor.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByFornecedorNomeFornecedorContainingIgnoreCase(nomeParcialFornecedor);
    }

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

}

