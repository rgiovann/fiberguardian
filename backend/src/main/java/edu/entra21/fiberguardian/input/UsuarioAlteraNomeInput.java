package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.EmailValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioAlteraNomeInput {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter até 100 caracteres")
    private String nome;

}
