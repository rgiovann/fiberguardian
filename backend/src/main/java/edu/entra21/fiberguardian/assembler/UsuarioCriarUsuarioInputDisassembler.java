package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import edu.entra21.fiberguardian.model.Usuario;

@Component
public class UsuarioCriarUsuarioInputDisassembler extends EntityInputDisassembler<UsuarioCompletoComSenhaInput, Usuario> {

	public UsuarioCriarUsuarioInputDisassembler(Mapper mapper) {
		super((ModelMapper) mapper);
	}

}
