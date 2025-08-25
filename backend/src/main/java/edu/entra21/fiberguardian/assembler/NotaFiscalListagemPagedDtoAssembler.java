package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.NotaFiscalListagemPagedDto;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalListagemPagedDtoAssembler extends EntitytDtoAssembler<NotaFiscalListagemPagedDto, NotaFiscal> {

    public NotaFiscalListagemPagedDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
