package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.ItemNotaFiscalDto;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class ItemNotaFiscalDtoAssembler extends EntitytDtoAssembler<ItemNotaFiscalDto, ItemNotaFiscal> {

    public ItemNotaFiscalDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
