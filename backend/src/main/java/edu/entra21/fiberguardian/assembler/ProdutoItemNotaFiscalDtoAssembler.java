package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.ProdutoItemNotaFiscalDto;
import edu.entra21.fiberguardian.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoItemNotaFiscalDtoAssembler extends EntitytDtoAssembler<ProdutoItemNotaFiscalDto, Produto> {

	public ProdutoItemNotaFiscalDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
