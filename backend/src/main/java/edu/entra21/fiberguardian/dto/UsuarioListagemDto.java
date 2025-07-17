package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioListagemDto {

	private String nome;
	private String email;
	private Role role;

}