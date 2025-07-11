package edu.entra21.fiberguardian.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioDtoAssembler extends EntitytDtoAssembler<UsuarioDto, Usuario> {

	public UsuarioDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
