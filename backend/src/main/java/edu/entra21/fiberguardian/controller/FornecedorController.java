package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.FornecedorDtoAssembler;
import edu.entra21.fiberguardian.assembler.FornecedorInputDisassembler;
import edu.entra21.fiberguardian.assembler.FornecedorListagemDtoAssembler;
import edu.entra21.fiberguardian.dto.FornecedorDto;
import edu.entra21.fiberguardian.dto.FornecedorListagemDto;
import edu.entra21.fiberguardian.dto.PageDto;
import edu.entra21.fiberguardian.dto.UsuarioListagemDto;
import edu.entra21.fiberguardian.input.FornecedorInput;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.service.FornecedorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/fornecedores", produces = MediaType.APPLICATION_JSON_VALUE)
public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorDtoAssembler fornecedorDtoAssembler;
    private final FornecedorInputDisassembler fornecedorInputDisassembler;
    private final FornecedorListagemDtoAssembler fornecedorListagemDtoAssembler;

    private static final int TAMANHO_PAGINA_PADRAO = 10;
    private static final Sort ORDENACAO_PADRAO =
            Sort.by(Sort.Order.desc("nomeFornecedor"));

    private static final Logger logger = LoggerFactory.getLogger(FornecedorController.class);

    public FornecedorController(FornecedorService fornecedorService,
                                FornecedorDtoAssembler fornecedorDtoAssembler,
                                FornecedorInputDisassembler fornecedorInputDisassembler,
                                FornecedorListagemDtoAssembler fornecedorListagemDtoAssembler) {
        this.fornecedorService = fornecedorService;
        this.fornecedorDtoAssembler = fornecedorDtoAssembler;
        this.fornecedorInputDisassembler = fornecedorInputDisassembler;
        this.fornecedorListagemDtoAssembler = fornecedorListagemDtoAssembler;
    }


    @GetMapping(path = "/nomes")
    @JsonView(FornecedorView.SomenteNome.class)
    public List<FornecedorDto> listarFiltroPorNome(@RequestParam(required = false) String nome) {
        List<Fornecedor> fornecedores = fornecedorService.listarFiltroPorNome(nome);
        return fornecedorDtoAssembler.toCollectionDto(fornecedores);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<FornecedorListagemDto> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, ORDENACAO_PADRAO);

        Page<Fornecedor> pagina = fornecedorService.listarPaginado(pageable);
        List<FornecedorListagemDto> dtos = fornecedorListagemDtoAssembler.toCollectionDto(pagina.getContent());

        PageDto<FornecedorListagemDto> dtoPaged = new PageDto<>();
        dtoPaged.setContent(dtos);
        dtoPaged.setPageNumber(pagina.getNumber());
        dtoPaged.setPageSize(pagina.getSize());
        dtoPaged.setTotalElements(pagina.getTotalElements());
        dtoPaged.setTotalPages(pagina.getTotalPages());
        dtoPaged.setLast(pagina.isLast());

        return dtoPaged;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorDto adicionar(@RequestBody @Valid FornecedorInput fornecedorInput) {
        fornecedorService.validarInsercao(fornecedorInput.getCnpj(), fornecedorInput.getNomeFornecedor());

        Fornecedor fornecedor = fornecedorInputDisassembler.toEntity(fornecedorInput);
        fornecedor = fornecedorService.salvar(fornecedor);
        return fornecedorDtoAssembler.toDto(fornecedor);
    }

    @JsonView(FornecedorView.Completo.class)
    @GetMapping(value= "/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorDto buscar(@PathVariable String cnpj) {

        return  fornecedorDtoAssembler.toDto(fornecedorService.buscarPorCNPJObrigatorio(cnpj));

    }

    @DeleteMapping("/{cnpj}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String cnpj) {
        fornecedorService.excluir(cnpj);
    }

    @PutMapping(value= "/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorDto atualizar(@PathVariable String cnpj, @RequestBody @Valid FornecedorInput fornecedorInput) {

        fornecedorService.validarAlteracao(cnpj, fornecedorInput.getCnpj(), fornecedorInput.getNomeFornecedor());

        Fornecedor fornecedor = fornecedorService.buscarPorCNPJObrigatorio(cnpj);

        fornecedorInputDisassembler.copyToEntity(fornecedorInput, fornecedor);

        return fornecedorDtoAssembler.toDto(fornecedorService.salvar(fornecedor));
    }
}

