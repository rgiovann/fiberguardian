package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.FornecedorDto;
import edu.entra21.fiberguardian.dto.ProdutoDto;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class FornecedorDtoAssembler extends EntitytDtoAssembler<FornecedorDto, Fornecedor> {

	public FornecedorDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
