package edu.entra21.fiberguardian.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.input.UsuarioInput;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioCriarUsuarioInputDisassembler extends EntityInputDisassembler<UsuarioInput.CriarUsuario, Usuario> {

	public UsuarioCriarUsuarioInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}

}
