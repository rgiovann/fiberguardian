package edu.entra21.fiberguardian.input;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorInput {

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 255, message = "Nome deve ter até 255 caracteres")
	private String nomeFornecedor;

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 45, message = "Nome deve ter até 45 caracteres")
	private String cnpj;
}