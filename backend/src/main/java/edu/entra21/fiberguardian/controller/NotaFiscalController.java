package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.ItemNotaFiscalInputDisassembler;
import edu.entra21.fiberguardian.assembler.PdfNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.dto.PdfNotaFiscalDto;
import edu.entra21.fiberguardian.input.NotaFiscalComItensInput;
import edu.entra21.fiberguardian.assembler.NotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.assembler.NotaFiscalInputDisassembler;
import edu.entra21.fiberguardian.dto.NotaFiscalDto;
import edu.entra21.fiberguardian.input.PdfNotaFiscalInput;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import edu.entra21.fiberguardian.service.PdfNotalFiscalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nota-fiscal")
public class NotaFiscalController {


    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalController.class);
    private final NotaFiscalService notaFiscalService;
    private final NotaFiscalInputDisassembler notaFiscalInputDisassembler;
    private final ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler;
    private final NotaFiscalDtoAssembler notaFiscalDtoAssembler;
    private final PdfNotalFiscalService pdfNotalFiscalService;
    private final PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler;

    public NotaFiscalController(
            NotaFiscalService notaFiscalService,
            NotaFiscalInputDisassembler notaFiscalInputDisassembler,
            ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler,
            NotaFiscalDtoAssembler notaFiscalDtoAssembler, PdfNotalFiscalService pdfNotalFiscalService, PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler) {
        this.notaFiscalService = notaFiscalService;
        this.notaFiscalInputDisassembler = notaFiscalInputDisassembler;
        this.itemNotaFiscalInputDisassembler = itemNotaFiscalInputDisassembler;
        this.notaFiscalDtoAssembler = notaFiscalDtoAssembler;
        this.pdfNotalFiscalService = pdfNotalFiscalService;
        this.pdfNotaFiscalDtoAssembler = pdfNotaFiscalDtoAssembler;
    }

    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
    @GetMapping("/list")
    public List<NotaFiscalDto> listarPorCodigo(@RequestParam String codigo) {
        return notaFiscalDtoAssembler.toCollectionDto(
                notaFiscalService.buscarPorCodigoNf(codigo)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
    public NotaFiscalDto salvarComPdf(
            @Valid @RequestPart("dadosNota") NotaFiscalComItensInput notaInput,
            @Valid @RequestPart("pdfMeta") PdfNotaFiscalInput pdfInput,
            @RequestPart("arquivo") MultipartFile arquivo
    ) throws IOException {

        // 1. Converter Input -> Entidade NotaFiscal
        NotaFiscal nota = notaFiscalInputDisassembler.toEntity(notaInput.getNota());

        List<ItemNotaFiscal> itens = notaInput.getItens().stream()
                .map(itemNotaFiscalInputDisassembler::toEntity)
                .collect(Collectors.toList());

        PdfNotaFiscal pdf = new PdfNotaFiscal();
        pdf.setDescricao(pdfInput.getDescricao());
        pdf.setContentType(arquivo.getContentType());
        pdf.setTamanho(arquivo.getSize());
        pdf.setNomeArquivo(arquivo.getOriginalFilename());

        NotaFiscal notaSalva = notaFiscalService.salvarNotaComItensEPdf(
                nota, itens, pdf, arquivo.getInputStream());

        NotaFiscalDto  notaFiscalDto = notaFiscalDtoAssembler.toDto(notaSalva);
        PdfNotaFiscalDto pdfNotaFiscalDto = pdfNotaFiscalDtoAssembler.toDto(pdfNotalFiscalService.buscarOuFalhar(notaFiscalDto.getId()));

        notaFiscalDto.setPdfMetadados(pdfNotaFiscalDto);

        return notaFiscalDto;
    }
//    @PostMapping()
//    @JsonView(NotaFiscalView.NotafiscalRespostaDto.class)
//    public  NotaFiscalDto  salvar(@RequestBody @Valid NotaFiscalComItensInput input) {
//        NotaFiscal nota = notaFiscalInputDisassembler.toEntity(input.getNota());
//
//        List<ItemNotaFiscal> itens = input.getItens().stream()
//                .map(itemNotaFiscalInputDisassembler::toEntity)
//                .collect(Collectors.toList()); // retorna ArrayList mutável
//
//        NotaFiscal notaSalva = notaFiscalService.salvarComItens(nota, itens);
//
//        return  notaFiscalDtoAssembler.toDto(notaSalva);
//    }

}
