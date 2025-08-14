package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.ProdutoDtoAssembler;
import edu.entra21.fiberguardian.assembler.ProdutoInputDisassembler;
import edu.entra21.fiberguardian.assembler.ProdutoListagemPagedDtoAssembler;
import edu.entra21.fiberguardian.dto.PageDto;
import edu.entra21.fiberguardian.dto.ProdutoDto;
import edu.entra21.fiberguardian.dto.ProdutoListagemPagedDto;
import edu.entra21.fiberguardian.input.ProdutoInput;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import edu.entra21.fiberguardian.model.Produto;
import edu.entra21.fiberguardian.service.ProdutoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    private final ProdutoService produtoService;
    private final ProdutoDtoAssembler produtoDtoAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;
    private final ProdutoListagemPagedDtoAssembler produtoListagemPagedDtoAssembler;
    private static final int TAMANHO_PAGINA_PADRAO = 10;
    private static final Sort ORDENACAO_PADRAO =
            Sort.by(Sort.Order.desc("codigo"));

    public ProdutoController(ProdutoService produtoService,
                             ProdutoDtoAssembler produtoDtoAssembler,
                             ProdutoInputDisassembler produtoInputDisassembler,
                             ProdutoListagemPagedDtoAssembler produtoListagemPagedDtoAssembler) {
        this.produtoService = produtoService;
        this.produtoDtoAssembler = produtoDtoAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
        this.produtoListagemPagedDtoAssembler = produtoListagemPagedDtoAssembler;
    }

    @PostMapping("/{cnpjFornecedor}")
    @JsonView(ProdutoView.Completo.class)
    public ProdutoDto adicionar(@PathVariable String cnpjFornecedor, @RequestBody @Valid ProdutoInput input) {
        Produto produto = produtoInputDisassembler.toEntity(input);
        return produtoDtoAssembler.toDto(produtoService.salvar(cnpjFornecedor, produto));
    }

    @PutMapping("/{cnpjFornecedor}/{codigoProduto}")
    @JsonView(ProdutoView.Completo.class)
    public ProdutoDto atualizar(
            @PathVariable String cnpjFornecedor,
            @PathVariable String codigoProduto,
            @RequestBody @Valid ProdutoInput input) {

        Produto produto = produtoInputDisassembler.toEntity(input);
        return produtoDtoAssembler.toDto(produtoService.atualizar(cnpjFornecedor, codigoProduto, produto));
    }

    @GetMapping("/{cnpjFornecedor}/{codigoProduto}")
    public ProdutoDto buscar(@PathVariable String cnpjFornecedor, @PathVariable String codigoProduto) {
        Produto  produto  = produtoService.buscarPorCnpjECodigoObrigatorio(cnpjFornecedor, codigoProduto);
        return produtoDtoAssembler.toDto(produto);
    }

    @GetMapping("/paged")
    public PageDto<ProdutoListagemPagedDto> listarPaginado(
            @RequestParam String cnpj,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, ORDENACAO_PADRAO);

        Page<Produto> pagina = produtoService.listarTodosPaginadoFiltroCnpj(cnpj,pageable);
        List<ProdutoListagemPagedDto> dtos = produtoListagemPagedDtoAssembler.toCollectionDto(pagina.getContent());

        PageDto<ProdutoListagemPagedDto> dtoPaged = new PageDto<>();
        dtoPaged.setContent(dtos);
        dtoPaged.setPageNumber(pagina.getNumber());
        dtoPaged.setPageSize(pagina.getSize());
        dtoPaged.setTotalElements(pagina.getTotalElements());
        dtoPaged.setTotalPages(pagina.getTotalPages());
        dtoPaged.setLast(pagina.isLast());

        return dtoPaged;
    }

    @JsonView(ProdutoView.SomenteCodigoEDescricao.class)
    @GetMapping("/list")
    public List<ProdutoDto> listarTodos(@RequestParam String cnpj) {
         return produtoDtoAssembler.toCollectionDto(produtoService.listarTodosFiltroCnpj(cnpj));
    }

    @DeleteMapping("/{cnpjFornecedor}/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable String cnpjFornecedor,
                         @PathVariable String codigoProduto) {
        produtoService.excluir(cnpjFornecedor,codigoProduto);
    }
}

