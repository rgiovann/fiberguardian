package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.FornecedorListagemDto;
import edu.entra21.fiberguardian.dto.UsuarioListagemDto;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FornecedorListagemDtoAssembler extends EntitytDtoAssembler<FornecedorListagemDto, Fornecedor> {

	public FornecedorListagemDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
