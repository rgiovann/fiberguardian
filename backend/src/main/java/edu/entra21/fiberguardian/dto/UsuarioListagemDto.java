package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.Role;
import edu.entra21.fiberguardian.model.Setor;
import edu.entra21.fiberguardian.model.Turno;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioListagemDto {

	private String nome;
	private String email;
	private Role role;
	private Boolean ativo;
	private Setor setor;
	private Turno turno;

}