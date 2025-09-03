package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.model.Laboratorio;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioInputDisassembler
        extends EntityInputDisassembler<edu.entra21.fiberguardian.input.LaboratorioInput, Laboratorio> {

    public LaboratorioInputDisassembler(Mapper mapper) {
        super(mapper);
    }
}
