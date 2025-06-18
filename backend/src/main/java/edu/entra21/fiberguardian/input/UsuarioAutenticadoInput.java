package edu.entra21.fiberguardian.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class UsuarioAutenticadoInput {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter até 50 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 50, message = "Senha deve ter até 50 caracteres")
    @ToString.Exclude
    private String senha;
}
