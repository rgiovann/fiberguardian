package edu.entra21.fiberguardian.assembler;

import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.UsuarioListagemDto;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioListagemDtoAssembler extends EntitytDtoAssembler<UsuarioListagemDto, Usuario> {

	public UsuarioListagemDtoAssembler(Mapper mapper) {
		super(mapper);
	}

}
