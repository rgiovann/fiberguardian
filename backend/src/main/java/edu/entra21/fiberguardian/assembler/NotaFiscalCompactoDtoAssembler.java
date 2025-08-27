package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.NotaFiscalCompactoDto;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalCompactoDtoAssembler extends EntitytDtoAssembler<NotaFiscalCompactoDto, NotaFiscal> {

    public NotaFiscalCompactoDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
