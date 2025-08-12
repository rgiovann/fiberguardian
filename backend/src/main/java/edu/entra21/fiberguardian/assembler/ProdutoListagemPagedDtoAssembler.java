package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.ProdutoListagemPagedDto;
import edu.entra21.fiberguardian.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoListagemPagedDtoAssembler extends EntitytDtoAssembler<ProdutoListagemPagedDto, Produto> {

	public ProdutoListagemPagedDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
