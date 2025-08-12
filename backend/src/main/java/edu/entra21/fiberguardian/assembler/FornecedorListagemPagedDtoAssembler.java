package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.FornecedorListagemPagedDto;
import edu.entra21.fiberguardian.model.Fornecedor;
import org.springframework.stereotype.Component;

@Component
public class FornecedorListagemPagedDtoAssembler extends EntitytDtoAssembler<FornecedorListagemPagedDto, Fornecedor> {

	public FornecedorListagemPagedDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
