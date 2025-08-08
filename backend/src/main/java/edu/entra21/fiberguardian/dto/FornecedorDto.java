package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import edu.entra21.fiberguardian.model.Fornecedor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorDto {

	@JsonView({FornecedorView.Completo.class,FornecedorView.SomenteNome.class})
	private String nomeFornecedor;

	@JsonView(FornecedorView.Completo.class)
	private String cnpj;
 
}