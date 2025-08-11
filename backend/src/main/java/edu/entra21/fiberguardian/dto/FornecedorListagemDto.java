package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.Role;
import edu.entra21.fiberguardian.model.Setor;
import edu.entra21.fiberguardian.model.Turno;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorListagemDto {

	private String nomeFornecedor;
	private String cnpj;

}