package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.dto.RelatorioTesteDTO;
import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.exception.exception.LaboratorioNaoEncontradoException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.input.LaboratorioRelatorioInput;
import edu.entra21.fiberguardian.model.*;
import edu.entra21.fiberguardian.repository.EngenhariaRepository;
import edu.entra21.fiberguardian.repository.ItemNotaFiscalRepository;
import edu.entra21.fiberguardian.repository.LaboratorioRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true) //  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final FornecedorService fornecedorService;
    private final NotaFiscalService notaFiscalService;
    private final ProdutoService produtoService;
    private final ItemNotaFiscalService itemNotaFiscalService;
    private final ItemNotaFiscalRepository itemNotaFiscalRepository;
    private final UsuarioService usuarioService;
    private final EngenhariaService engenhariaService;
    private static final String MSG_LABORATORIO_EM_USO = "Não é possível deletar, existe um laudo de engenharia associado a esse laudo de laboratório.";

    public LaboratorioService(LaboratorioRepository laboratorioRepository,
                              FornecedorService fornecedorService,
                              NotaFiscalService notaFiscalService,
                              ProdutoService produtoService,
                              ItemNotaFiscalService itemNotaFiscalService, ItemNotaFiscalRepository itemNotaFiscalRepository, UsuarioService usuarioService, EngenhariaService engenhariaService) {
        this.laboratorioRepository = laboratorioRepository;
        this.fornecedorService = fornecedorService;
        this.notaFiscalService = notaFiscalService;
        this.produtoService = produtoService;
        this.itemNotaFiscalService = itemNotaFiscalService;
        this.itemNotaFiscalRepository = itemNotaFiscalRepository;
        this.usuarioService = usuarioService;
        this.engenhariaService = engenhariaService;
    }

    @Transactional(readOnly = false)
    public Laboratorio salvar(Laboratorio laboratorio) {
        String cnpj = laboratorio.getItemNotaFiscal().getNotaFiscal().getFornecedor().getCnpj();
        String codigoNf = laboratorio.getItemNotaFiscal().getNotaFiscal().getCodigoNf();
        String codProduto = laboratorio.getItemNotaFiscal().getProduto().getCodigo();

        // a) validar fornecedor
        fornecedorService.validaFornecedor(cnpj);

        // b) validar nota fiscal
        NotaFiscal nota = notaFiscalService.buscarObrigatorioPorNotaECnpj(cnpj, codigoNf);

        // c) validar produto
        Produto produto = produtoService.buscarPorCnpjECodigoObrigatorio(cnpj, codProduto);

        // d) validar item da nota fiscal (precisamos criar este método)
        ItemNotaFiscal item = itemNotaFiscalService.buscarPorNotaFiscalEProduto(nota.getId(), produto.getId());

        // e) validar usuario

        Usuario usuario = usuarioService.buscarPorEmailObrigatorio(laboratorio.getLiberadoPor().getEmail());

        // associar entidades reais ao laboratório
        laboratorio.setItemNotaFiscal(item);
        laboratorio.setLiberadoPor(usuario);

        return laboratorioRepository.save(laboratorio);
    }

    /**
     * Verifica se o laboratório existe pelo id.
     * @param idLaboratorio ID do laboratório
     * @return true se existir
     * @throws LaboratorioNaoEncontradoException se não existir
     */
    public boolean verificarExistenciaOuFalhar(Long idLaboratorio) {
        boolean exists = laboratorioRepository.existsById(idLaboratorio);
        if (!exists) {
            throw new LaboratorioNaoEncontradoException(
                    "Laudo de laboratório de id " + idLaboratorio + " não encontrado."
            );
        }
        return true;
    }


    @Transactional(readOnly = false)
    public void excluirLaboratorio(Long laboratorioId) {

        verificarExistenciaOuFalhar(laboratorioId);

        if (engenhariaService.existeLaboratorioAssociado(laboratorioId)) {
            throw new NegocioException(
                    "Existe um registros de laudo de engenharia associado a esse teste de laboratório,  " +
                            " exclua esse registro antes de deletar esse registro."
            );
        }

        try {
            laboratorioRepository.deleteById(laboratorioId);
            laboratorioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_LABORATORIO_EM_USO ));
        }
    }


    public Laboratorio buscarOuFalhar(Long idLaboratorio) {
        return laboratorioRepository.findById(idLaboratorio)
                .orElseThrow(() -> new LaboratorioNaoEncontradoException(
                        "Laudo de laboratório de id " + idLaboratorio + " não encontrado."
                ));
    }

    public byte[] gerarRelatorioPDF(LaboratorioRelatorioInput dados) {
        try {
            // Com .jrxml + compilação runtime
            InputStream templateStream = new ClassPathResource("/reports/fiberguardian-01.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("TITULO", "Relatório Testes de Laboratório");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(dados));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage(), e);
        }
    }
}

