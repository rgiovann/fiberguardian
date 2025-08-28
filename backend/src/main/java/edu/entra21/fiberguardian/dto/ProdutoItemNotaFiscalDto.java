package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.ItemNotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoItemNotaFiscalDto {
	@JsonView({ ItemNotaFiscalView.ItemNotaFiscalListDto.class})
	private String codigo;
	@JsonView({ ItemNotaFiscalView.ItemNotaFiscalListDto.class})
	private String descricao;
}