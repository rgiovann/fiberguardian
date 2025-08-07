package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import edu.entra21.fiberguardian.jacksonview.UsuarioView;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Role;
import edu.entra21.fiberguardian.model.Setor;
import edu.entra21.fiberguardian.model.Turno;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoDto {

	@JsonView({ProdutoView.Completo.class,ProdutoView.SomenteCodigo.class})
	private String codigoProduto;

	@JsonView({ProdutoView.Completo.class,ProdutoView.class})
	private String descricaoProduto;

	@JsonView({ProdutoView.Completo.class,ProdutoView.class})
	private Fornecedor fornecedor;

}