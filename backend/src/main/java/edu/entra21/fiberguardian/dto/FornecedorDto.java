package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorDto {

	@JsonView({FornecedorView.Completo.class,
			   FornecedorView.SomenteNome.class,
	           NotaFiscalView.NotafiscalCompactoDto.class})
	private String nome;

	@JsonView({FornecedorView.Completo.class,
			FornecedorView.SomenteCnpj.class,
			ProdutoView.Completo.class,
			NotaFiscalView.NotafiscalRespostaDto.class,
			NotaFiscalView.NotafiscalCompactoDto.class})
	private String cnpj;

	@JsonView({FornecedorView.Completo.class})
	private String email;

	@JsonView({FornecedorView.Completo.class})
	private String telefone;

}