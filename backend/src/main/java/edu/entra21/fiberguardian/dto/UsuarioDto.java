package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;

import edu.entra21.fiberguardian.jacksonview.UsuarioView;
import edu.entra21.fiberguardian.model.Role;
import edu.entra21.fiberguardian.model.Setor;
import edu.entra21.fiberguardian.model.Turno;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDto {

	@JsonView({ UsuarioView.Completo.class, UsuarioView.SomenteNome.class, UsuarioView.SomenteNomeSetor.class  })
	private String nome;
	@JsonView({ UsuarioView.Completo.class, UsuarioView.SomenteEmail.class })
	private String email;
	@JsonView({ UsuarioView.Completo.class })
	private Role role;
	@JsonView({ UsuarioView.Completo.class, UsuarioView.SomenteNomeSetor.class })
	private Setor setor;
	@JsonView({ UsuarioView.Completo.class })
	private Turno turno;

}