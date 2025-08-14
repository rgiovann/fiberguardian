package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.NotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.dto.NotaFiscalDto;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nota-fiscal")
public class NotaFiscalController {

    private final NotaFiscalDtoAssembler notaFiscalDtoAssembler;
    private final NotaFiscalService notaFiscalService;

    public NotaFiscalController(NotaFiscalDtoAssembler notaFiscalDtoAssembler, NotaFiscalService notaFiscalService) {
        this.notaFiscalDtoAssembler = notaFiscalDtoAssembler;
        this.notaFiscalService = notaFiscalService;
    }

    @JsonView(NotaFiscalView.NFNomeFornecedorRecebidoPor.class)
    @GetMapping("/list")
    public List<NotaFiscalDto> listarPorCodigo(@RequestParam String codigo) {
        return notaFiscalDtoAssembler.toCollectionDto(
                notaFiscalService.buscarPorCodigoNf(codigo)
        );
    }

}
