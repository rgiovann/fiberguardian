package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.assembler.LaboratorioDtoAssembler;
import edu.entra21.fiberguardian.assembler.LaboratorioInputDisassembler;
import edu.entra21.fiberguardian.dto.LaboratorioDto;
import edu.entra21.fiberguardian.dto.LaboratorioListagemPagedDto;
import edu.entra21.fiberguardian.dto.PageDto;
import edu.entra21.fiberguardian.input.LaboratorioInput;
import edu.entra21.fiberguardian.input.LaboratorioRelatorioInput;
import edu.entra21.fiberguardian.model.Laboratorio;
import edu.entra21.fiberguardian.model.StatusLaboratorio;
import edu.entra21.fiberguardian.service.LaboratorioService;
import edu.entra21.fiberguardian.service.query.LaboratorioFilter;
import edu.entra21.fiberguardian.service.query.LaboratorioQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioInputDisassembler laboratorioInputDisassembler;
    private final LaboratorioDtoAssembler laboratorioDtoAssembler;
    private final LaboratorioService laboratorioService;
    private final LaboratorioQueryService laboratorioQueryService;
    private static final Logger logger = LoggerFactory.getLogger(LaboratorioController.class);

    public LaboratorioController(
            LaboratorioInputDisassembler laboratorioInputDisassembler,
            LaboratorioDtoAssembler laboratorioDtoAssembler,
            LaboratorioService laboratorioService, LaboratorioQueryService laboratorioQueryService) {
        this.laboratorioInputDisassembler = laboratorioInputDisassembler;
        this.laboratorioDtoAssembler = laboratorioDtoAssembler;
        this.laboratorioService = laboratorioService;
        this.laboratorioQueryService = laboratorioQueryService;
    }

    @PostMapping
    public LaboratorioDto adicionar(@RequestBody @Valid LaboratorioInput laboratorioInput) {
        Laboratorio laboratorio = laboratorioInputDisassembler.toEntity(laboratorioInput);
        laboratorio = laboratorioService.salvar(laboratorio);
        return laboratorioDtoAssembler.toDto(laboratorio);
    }


    @PostMapping("/pdf/{laboratorioId}")
    public ResponseEntity<byte[]> gerarRelatorio(
            @PathVariable Long laboratorioId,
            @RequestBody @Valid LaboratorioRelatorioInput input) {

         Laboratorio laboratorio = laboratorioService.buscarOuFalhar(laboratorioId);

        // Preenchendo os campos que o frontend não enviou
        if (input.getAlongamento() == null) {
            input.setAlongamento(laboratorio.getAlongamento());
        }

        if (input.getCvm() == null) {
            input.setCvm(laboratorio.getCvm());
        }

        if (input.getPontosFinos() == null) {
            input.setPontosFinos(laboratorio.getPontosFinos());
        }

        if (input.getPontosGrossos() == null) {
            input.setPontosGrossos(laboratorio.getPontosGrossos());
        }

        if (input.getNeps() == null) {
            input.setNeps(laboratorio.getNeps());
        }

        if (input.getPilosidade() == null) {
            input.setPilosidade(laboratorio.getPilosidade());
        }

        if (input.getResistencia() == null) {
            input.setResistencia(laboratorio.getResistencia());
        }

        if (input.getTituloNe() == null) {
            input.setTituloNe(laboratorio.getTituloNe());
        }

        if (input.getTorcaoTm() == null) {
            input.setTorcaoTm(laboratorio.getTorcaoTm());
        }

        byte[] pdfBytes = laboratorioService.gerarRelatorioPDF(input);

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));

        String filename = "relatorio_" + timestamp + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(pdfBytes);
    }

    @GetMapping("/paged")
    public PageDto<LaboratorioListagemPagedDto> listarPaginado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataini,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datafim,
            @RequestParam(required = false) String notafiscal,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) StatusLaboratorio status,
            @PageableDefault(size = 20, page=0) Pageable pageable

    ) {

        LaboratorioFilter filtro = new LaboratorioFilter();
        filtro.setDataRecebimentoInicio(dataini);
        filtro.setDataRecebimentoFim(datafim);
        filtro.setCodigoNf(notafiscal);
        filtro.setCnpj(cnpj);
        filtro.setEmail(email);
        filtro.setStatus(status);

        Page<LaboratorioListagemPagedDto> result = laboratorioQueryService.buscarLaudoPorFiltro(filtro, pageable);
        return new PageDto<>(result);
    }

    @DeleteMapping("/{laboratorioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long laboratorioId)
    {
        laboratorioService.excluirLaboratorio(laboratorioId);
    }

}
