package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.FornecedorDtoAssembler;
import edu.entra21.fiberguardian.assembler.FornecedorInputDisassembler;
import edu.entra21.fiberguardian.dto.FornecedorDto;
import edu.entra21.fiberguardian.input.FornecedorInput;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.service.FornecedorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FornecedorController.class);

    public FornecedorController(FornecedorService fornecedorService,
                                FornecedorDtoAssembler fornecedorDtoAssembler,
                                FornecedorInputDisassembler fornecedorInputDisassembler) {
        this.fornecedorService = fornecedorService;
        this.fornecedorDtoAssembler = fornecedorDtoAssembler;
        this.fornecedorInputDisassembler = fornecedorInputDisassembler;
    }


    @GetMapping(path = "/nomes")
    @JsonView(FornecedorView.SomenteNome.class)
    public List<FornecedorDto> listarSomenteNomes() {
        List<Fornecedor> fornecedores = fornecedorService.listar();
        return fornecedorDtoAssembler.toCollectionDto(fornecedores);
    }

    @GetMapping
    @JsonView(FornecedorView.Completo.class)
    public List<FornecedorDto> listarComNomeECnpj() {
        List<Fornecedor> fornecedores = fornecedorService.listar();
        return fornecedorDtoAssembler.toCollectionDto(fornecedores);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorDto adicionar(@RequestBody @Valid FornecedorInput fornecedorInput) {
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

    @PutMapping(value= "/{cnpj}",produces = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorDto atualizar(@PathVariable String cnpj, @RequestBody @Valid  FornecedorInput fornecedorInput)
    {
        Fornecedor fornecedor = fornecedorService.buscarPorCNPJObrigatorio(cnpj);

        fornecedorInputDisassembler.copyToEntity(fornecedorInput,fornecedor);

        return  fornecedorDtoAssembler.toDto(fornecedorService.salvar(fornecedor));


    }
}

