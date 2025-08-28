package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.ItemNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.assembler.ItemNotaFiscalInputDisassembler;
import edu.entra21.fiberguardian.dto.ItemNotaFiscalDto;
import edu.entra21.fiberguardian.input.ItemNotaFiscaSemCodigoNFInput;
import edu.entra21.fiberguardian.jacksonview.ItemNotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import edu.entra21.fiberguardian.service.ItemNotaFiscalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/item-notas-fiscais")
public class ItemNotaFiscalController {

    private final ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler;
    private final ItemNotaFiscalDtoAssembler itemNotaFiscalDtoAssembler;
    private final ItemNotaFiscalService itemNotaFiscalService;

    public ItemNotaFiscalController(ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler,
                                    ItemNotaFiscalDtoAssembler itemNotaFiscalDtoAssembler,
                                    ItemNotaFiscalService itemNotaFiscalService) {
        this.itemNotaFiscalInputDisassembler = itemNotaFiscalInputDisassembler;
        this.itemNotaFiscalDtoAssembler = itemNotaFiscalDtoAssembler;
        this.itemNotaFiscalService = itemNotaFiscalService;

    }

    @PostMapping()
    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
    public ItemNotaFiscalDto adicionar(@RequestBody @Valid ItemNotaFiscaSemCodigoNFInput itemNotaFiscaSemCodigoNFInput) {

        ItemNotaFiscal itemNotaFiscal = itemNotaFiscalInputDisassembler.toEntity(itemNotaFiscaSemCodigoNFInput);
        return itemNotaFiscalDtoAssembler.toDto(itemNotaFiscal);
    }

    @JsonView({ItemNotaFiscalView.ItemNotaFiscalListDto.class})
    @GetMapping("/list/{cnpj}/{codigoNF}")
    public List<ItemNotaFiscalDto> listarPorCodigo(@PathVariable String cnpj,
                                                   @PathVariable  String codigoNF) {
        return itemNotaFiscalDtoAssembler.toCollectionDto(
                itemNotaFiscalService.buscarItensPorNota(cnpj,codigoNF));

    }
}
