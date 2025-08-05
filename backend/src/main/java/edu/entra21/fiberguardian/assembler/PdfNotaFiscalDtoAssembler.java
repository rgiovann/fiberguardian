package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.dto.PdfNotaFiscalDto;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.configuration.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PdfNotaFiscalDtoAssembler extends EntitytDtoAssembler<PdfNotaFiscalDto, PdfNotaFiscal>{

    public PdfNotaFiscalDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}

