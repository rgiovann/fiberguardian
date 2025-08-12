package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoDto {

	@JsonView({ProdutoView.Completo.class, ProdutoView.SomenteCodigoEDescricao.class})
	private String codigo;

	@JsonView({ProdutoView.Completo.class, ProdutoView.SomenteCodigoEDescricao.class  })
	private String descricao;

	@JsonView({ProdutoView.Completo.class })
	private FornecedorDto fornecedor;

}