package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.SenhaValida;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class UsuarioCompletoComSenhaInput extends UsuarioCompletoSemSenhaInput {

	@SenhaValida
	@ToString.Exclude
	private String senha;
}