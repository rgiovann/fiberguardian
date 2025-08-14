package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.NotaFiscalDto;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalDtoAssembler extends EntitytDtoAssembler<NotaFiscalDto, NotaFiscal> {

    public NotaFiscalDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
