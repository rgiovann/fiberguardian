package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.NotaFiscalInput;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalInputDisassembler
		extends EntityInputDisassembler<NotaFiscalInput, NotaFiscal> {

	public NotaFiscalInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}
