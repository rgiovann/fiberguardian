package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.ItemNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.assembler.ItemNotaFiscalInputDisassembler;
import edu.entra21.fiberguardian.dto.ItemNotaFiscalDto;
import edu.entra21.fiberguardian.input.ItemNotaFiscaSemCodigoNFInput;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import edu.entra21.fiberguardian.service.ItemNotaFiscalService;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/item-notas-fiscais")
public class ItemNotaFiscalController {

    private final ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler;
    private final ItemNotaFiscalDtoAssembler itemNotaFiscalDtoAssembler;
    private final ItemNotaFiscalService itemNotaFiscalService;
    private final NotaFiscalService notaFiscalService;

    public ItemNotaFiscalController(ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler,
                                    ItemNotaFiscalDtoAssembler itemNotaFiscalDtoAssembler,
                                    ItemNotaFiscalService itemNotaFiscalService,
                                    NotaFiscalService notaFiscalService) {
        this.itemNotaFiscalInputDisassembler = itemNotaFiscalInputDisassembler;
        this.itemNotaFiscalDtoAssembler = itemNotaFiscalDtoAssembler;
        this.itemNotaFiscalService = itemNotaFiscalService;
        this.notaFiscalService = notaFiscalService;

    }

    @PostMapping()
    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
    public ItemNotaFiscalDto adicionar(@RequestBody @Valid ItemNotaFiscaSemCodigoNFInput itemNotaFiscaSemCodigoNFInput) {

        ItemNotaFiscal itemNotaFiscal = itemNotaFiscalInputDisassembler.toEntity(itemNotaFiscaSemCodigoNFInput);
        return itemNotaFiscalDtoAssembler.toDto(itemNotaFiscal);
    }
}
