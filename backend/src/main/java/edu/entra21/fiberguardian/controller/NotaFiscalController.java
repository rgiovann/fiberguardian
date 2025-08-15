package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.NotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.assembler.NotaFiscalInputDisassembler;
import edu.entra21.fiberguardian.dto.NotaFiscalDto;
import edu.entra21.fiberguardian.dto.ProdutoDto;
import edu.entra21.fiberguardian.input.NotaFiscalInput;
import edu.entra21.fiberguardian.input.ProdutoInput;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.Produto;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nota-fiscal")
public class NotaFiscalController {

    private final NotaFiscalDtoAssembler notaFiscalDtoAssembler;
    private final NotaFiscalService notaFiscalService;
    private final NotaFiscalInputDisassembler notaFiscalInputDisassembler;

    public NotaFiscalController(NotaFiscalDtoAssembler notaFiscalDtoAssembler,
                                NotaFiscalService notaFiscalService,
                                NotaFiscalInputDisassembler notaFiscalInputDisassembler) {
        this.notaFiscalDtoAssembler = notaFiscalDtoAssembler;
        this.notaFiscalService = notaFiscalService;
        this.notaFiscalInputDisassembler = notaFiscalInputDisassembler;
    }

    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
    @GetMapping("/list")
    public List<NotaFiscalDto> listarPorCodigo(@RequestParam String codigo) {
        return notaFiscalDtoAssembler.toCollectionDto(
                notaFiscalService.buscarPorCodigoNf(codigo)
        );
    }

    @PostMapping()
    @JsonView(ProdutoView.Completo.class)
    public NotaFiscalDto adicionar( @RequestBody @Valid NotaFiscalInput input) {
        NotaFiscal notaFiscal = notaFiscalInputDisassembler.toEntity(input);
        return notaFiscalDtoAssembler.toDto(notaFiscalService.salvar(notaFiscal));
    }

}
