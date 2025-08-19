package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.ItemNotaFiscaSemCodigoNFInput;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class ItemNotaFiscalInputDisassembler
		extends EntityInputDisassembler<ItemNotaFiscaSemCodigoNFInput, ItemNotaFiscal> {

	public ItemNotaFiscalInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}
