package edu.entra21.fiberguardian.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.assembler.*;
import edu.entra21.fiberguardian.dto.*;
import edu.entra21.fiberguardian.input.NotaFiscalComItensInput;
import edu.entra21.fiberguardian.input.PdfNotaFiscalInput;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import edu.entra21.fiberguardian.service.PdfNotalFiscalService;
import edu.entra21.fiberguardian.service.query.NotaFiscalFilter;
import edu.entra21.fiberguardian.service.query.NotaFiscalQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notas-fiscais")
public class NotaFiscalController {


    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalController.class);
    private final NotaFiscalService notaFiscalService;
    private final NotaFiscalInputDisassembler notaFiscalInputDisassembler;
    private final ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler;
    private final NotaFiscalDtoAssembler notaFiscalDtoAssembler;
    private final PdfNotalFiscalService pdfNotalFiscalService;
    private final PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler;
    private final NotaFiscalQueryService notaFiscalQueryService;
    private final NotaFiscalListagemPagedDtoAssembler notaFiscalListagemPagedDtoAssembler;
    private final NotaFiscalCompactoDtoAssembler notaFiscalCompactoDtoAssembler;
    private static final Sort ORDENACAO_PADRAO =
            Sort.by(Sort.Order.desc("codigoNf"));


    public NotaFiscalController(
            NotaFiscalService notaFiscalService,
            NotaFiscalInputDisassembler notaFiscalInputDisassembler,
            ItemNotaFiscalInputDisassembler itemNotaFiscalInputDisassembler,
            NotaFiscalDtoAssembler notaFiscalDtoAssembler,
            PdfNotalFiscalService pdfNotalFiscalService,
            PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler,
            NotaFiscalQueryService notaFiscalQueryService,
            NotaFiscalListagemPagedDtoAssembler notaFiscalListagemPagedDtoAssembler,
            NotaFiscalCompactoDtoAssembler notaFiscalCompactoDtoAssembler) {

        this.notaFiscalService = notaFiscalService;
        this.notaFiscalInputDisassembler = notaFiscalInputDisassembler;
        this.itemNotaFiscalInputDisassembler = itemNotaFiscalInputDisassembler;
        this.notaFiscalDtoAssembler = notaFiscalDtoAssembler;
        this.pdfNotalFiscalService = pdfNotalFiscalService;
        this.pdfNotaFiscalDtoAssembler = pdfNotaFiscalDtoAssembler;
        this.notaFiscalQueryService = notaFiscalQueryService;
        this.notaFiscalListagemPagedDtoAssembler = notaFiscalListagemPagedDtoAssembler;
        this.notaFiscalCompactoDtoAssembler = notaFiscalCompactoDtoAssembler;
    }

    @JsonView(NotaFiscalView.NotafiscalCompactoDto.class)
    @GetMapping("/list")
    public List<NotaFiscalCompactoDto> listarPorCodigo(@RequestParam(required = false) String codigo) {
        return notaFiscalCompactoDtoAssembler.toCollectionDto(
                notaFiscalService.buscarPorCodigoNf(codigo)
        );
    }

    @JsonView(NotaFiscalView.NotafiscalCompactoDto.class)
    @GetMapping("/list/por_fornecedor/{cnpj}")
    public List<NotaFiscalCompactoDto> listarPorFornecedorECodigo(
            @PathVariable("cnpj") String cnpj,
            @RequestParam(name = "codigo_nf", required = false) String codigoNf
    ) {
        return notaFiscalCompactoDtoAssembler.toCollectionDto(
                notaFiscalService.buscarPorFornecedorECodigoNfParcial(cnpj, codigoNf)
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

    @GetMapping("/paged")
    public PageDto<NotaFiscalListagemPagedDto> listarPaginado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataini,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datafim,
            @RequestParam(required = false) String nfCodigo,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String produtoCodigo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // Monta o filtro
        NotaFiscalFilter filtro = new NotaFiscalFilter();
        filtro.setDataRecebimentoInicio(dataini);
        filtro.setDataRecebimentoFim(datafim);
        filtro.setCodigoNf(nfCodigo);
        filtro.setFornecedorCnpj(cnpj);
        filtro.setProdutoCodigo(produtoCodigo);

        Pageable pageable = PageRequest.of(page, size, ORDENACAO_PADRAO);
        Page<NotaFiscal> pagina = notaFiscalQueryService.consultarNotas(filtro,pageable);
        List<NotaFiscalListagemPagedDto> dtos = notaFiscalListagemPagedDtoAssembler.toCollectionDto(pagina.getContent());

        PageDto<NotaFiscalListagemPagedDto> dtoPaged = new PageDto<>();
        dtoPaged.setContent(dtos);
        dtoPaged.setPageNumber(pagina.getNumber());
        dtoPaged.setPageSize(pagina.getSize());
        dtoPaged.setTotalElements(pagina.getTotalElements());
        dtoPaged.setTotalPages(pagina.getTotalPages());
        dtoPaged.setLast(pagina.isLast());

        return dtoPaged;
    }

    @DeleteMapping("/{cnpj}/{codigoNF}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String cnpj,
                        @PathVariable String codigoNF) {
        notaFiscalService.excluirNotaFiscal(cnpj, codigoNF);
    }

}
