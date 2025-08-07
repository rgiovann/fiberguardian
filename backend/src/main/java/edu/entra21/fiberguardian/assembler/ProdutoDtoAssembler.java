package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.ProdutoDto;
import edu.entra21.fiberguardian.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDtoAssembler extends EntitytDtoAssembler<ProdutoDto, Produto> {

	public ProdutoDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
