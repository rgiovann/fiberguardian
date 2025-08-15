package edu.entra21.fiberguardian.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorListagemPagedDto {

	private String nomeFornecedor;
	private String cnpj;
	private String email;
	private String telefone;

}