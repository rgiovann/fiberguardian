package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class UsuarioInput {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter até 50 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter até 50 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 50, message = "Senha deve ter até 50 caracteres")
    @ToString.Exclude
    private String senha;

    @NotNull(message = "Ativo é obrigatório")
    private Boolean ativo = true;

    @NotNull(message = "Role é obrigatória")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USUARIO;

}
