package edu.entra21.fiberguardian.assembler;

import org.springframework.stereotype.Component;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioNovoInputDisassembler
		extends EntityInputDisassembler<UsuarioCompletoComSenhaInput, Usuario> {

	public UsuarioNovoInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}
