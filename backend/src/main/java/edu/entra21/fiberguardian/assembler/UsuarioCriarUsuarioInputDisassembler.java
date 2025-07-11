package edu.entra21.fiberguardian.assembler;

import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.UsuarioEmailSenhaInput;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioCriarUsuarioInputDisassembler extends EntityInputDisassembler<UsuarioEmailSenhaInput, Usuario> {

	public UsuarioCriarUsuarioInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}
