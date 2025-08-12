package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.FornecedorView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorCnpjDto {

 	private String cnpj;

}