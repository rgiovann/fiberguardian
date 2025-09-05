package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.assembler.LaboratorioDtoAssembler;
import edu.entra21.fiberguardian.assembler.LaboratorioInputDisassembler;
import edu.entra21.fiberguardian.dto.LaboratorioDto;
import edu.entra21.fiberguardian.input.LaboratorioInput;
import edu.entra21.fiberguardian.model.Laboratorio;
import edu.entra21.fiberguardian.service.LaboratorioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioInputDisassembler laboratorioInputDisassembler;
    private final LaboratorioDtoAssembler laboratorioDtoAssembler;
    private final LaboratorioService laboratorioService;
    private static final Logger logger = LoggerFactory.getLogger(LaboratorioController.class);

    public LaboratorioController(
            LaboratorioInputDisassembler laboratorioInputDisassembler,
            LaboratorioDtoAssembler laboratorioDtoAssembler,
            LaboratorioService laboratorioService) {
        this.laboratorioInputDisassembler = laboratorioInputDisassembler;
        this.laboratorioDtoAssembler = laboratorioDtoAssembler;
        this.laboratorioService = laboratorioService;
    }

    @PostMapping
    public LaboratorioDto adicionar(@RequestBody @Valid LaboratorioInput laboratorioInput) {
        Laboratorio laboratorio = laboratorioInputDisassembler.toEntity(laboratorioInput);
        laboratorio = laboratorioService.salvar(laboratorio);
        return laboratorioDtoAssembler.toDto(laboratorio);
    }
}
