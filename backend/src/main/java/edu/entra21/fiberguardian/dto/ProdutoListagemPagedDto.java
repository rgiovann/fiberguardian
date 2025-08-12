package edu.entra21.fiberguardian.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoListagemPagedDto {

	private String codigo;
	private String descricao;
	private FornecedorCnpjDto fornecedor;
}