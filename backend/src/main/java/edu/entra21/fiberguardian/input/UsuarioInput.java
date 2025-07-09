package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UsuarioInput {

	@Setter
	@Getter
	public static class CriarUsuario {
		@NotBlank(message = "Nome é obrigatório")
		@Size(max = 100, message = "Nome deve ter até 100 caracteres")
		private String nome;

		@NotBlank(message = "Email é obrigatório")
		@Email(message = "Email deve ser válido")
		@Size(max = 50, message = "Email deve ter até 50 caracteres")
		private String email;

		@NotBlank(message = "Senha é obrigatória")
		@Size(max = 50, message = "Senha deve ter até 50 caracteres")
		@ToString.Exclude
		private String senha;

		@NotBlank(message = "Repetir senha é obrigatório")
		@Size(max = 50, message = "Repetir senha deve ter até 50 caracteres")
		@ToString.Exclude
		private String repeteSenha;

		@NotNull(message = "Role é obrigatória")
		@Enumerated(EnumType.STRING)
		private Role role;
	}

	@Setter
	@Getter
	public static class HabilitarDesabilitarUsuario {
		@NotNull(message = "ID é obrigatório")
		@Positive(message = "ID deve ser positivo")
		private Long id;

		@NotNull(message = "Ativo é obrigatório")
		private Boolean ativo;
	}

	@Setter
	@Getter
	public static class AlterarUsuario {
		@NotNull(message = "ID é obrigatório")
		@Positive(message = "ID deve ser positivo")
		private Long id;

		@NotBlank(message = "Nome é obrigatório")
		@Size(max = 100, message = "Nome deve ter até 100 caracteres")
		private String nome;

		@NotNull(message = "Role é obrigatória")
		@Enumerated(EnumType.STRING)
		private Role role;
	}

	@Setter
	@Getter
	public static class AlterarSenha {
		@NotBlank(message = "Senha anterior é obrigatória")
		@Size(max = 50, message = "Senha anterior deve ter até 50 caracteres")
		@ToString.Exclude
		private String senhaAnterior;

		@NotBlank(message = "Nova senha é obrigatória")
		@Size(max = 50, message = "Nova senha deve ter até 50 caracteres")
		@ToString.Exclude
		private String senhaNova;

		@NotBlank(message = "Repetir senha é obrigatório")
		@Size(max = 50, message = "Repetir senha deve ter até 50 caracteres")
		@ToString.Exclude
		private String repeteSenha;
	}

	@Setter
	@Getter
	public static class UsuarioAutenticado {

		@NotBlank(message = "Email é obrigatório")
		@Email(message = "Email deve ser válido")
		@Size(max = 50, message = "Email deve ter até 50 caracteres")
		private String email;

		@NotBlank(message = "Senha é obrigatória")
		@Size(max = 50, message = "Senha deve ter até 50 caracteres")
		@ToString.Exclude
		private String senha;
	}

}
