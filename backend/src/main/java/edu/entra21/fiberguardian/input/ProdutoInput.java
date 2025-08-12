package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.CnpjNotInvalid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome dp produto deve ter até 100 caracteres")
    private String codigo;

    @NotBlank(message = "Código do produto é obrigatório")
    @Size(max = 255, message = "Código do produto deve ter até 255 caracteres")
    private String descricao;

}
