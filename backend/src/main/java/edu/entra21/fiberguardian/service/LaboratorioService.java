package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.model.*;
import edu.entra21.fiberguardian.repository.ItemNotaFiscalRepository;
import edu.entra21.fiberguardian.repository.LaboratorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public LaboratorioService(LaboratorioRepository laboratorioRepository,
                              FornecedorService fornecedorService,
                              NotaFiscalService notaFiscalService,
                              ProdutoService produtoService,
                              ItemNotaFiscalService itemNotaFiscalService, ItemNotaFiscalRepository itemNotaFiscalRepository, UsuarioService usuarioService) {
        this.laboratorioRepository = laboratorioRepository;
        this.fornecedorService = fornecedorService;
        this.notaFiscalService = notaFiscalService;
        this.produtoService = produtoService;
        this.itemNotaFiscalService = itemNotaFiscalService;
        this.itemNotaFiscalRepository = itemNotaFiscalRepository;
        this.usuarioService = usuarioService;
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

}

