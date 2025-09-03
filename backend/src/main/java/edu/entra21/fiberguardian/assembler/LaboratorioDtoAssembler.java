package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.LaboratorioDto;
import edu.entra21.fiberguardian.model.Laboratorio;
import org.springframework.stereotype.Component;

@Component

public class LaboratorioDtoAssembler extends EntitytDtoAssembler<LaboratorioDto, Laboratorio> {

    public LaboratorioDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
