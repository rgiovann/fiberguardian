package edu.entra21.fiberguardian.assembler;

import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioAutenticadoInputAssembler
		extends EntityInputDisassembler<UsuarioCompletoComSenhaInput, Usuario> {

	public UsuarioAutenticadoInputAssembler(Mapper mapper) {
		super(mapper);
	}

}
