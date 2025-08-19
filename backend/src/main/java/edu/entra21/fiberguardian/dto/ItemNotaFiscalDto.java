package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.ProdutoView;
import edu.entra21.fiberguardian.model.Produto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemNotaFiscalDto {
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private ProdutoDto produto;
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private BigDecimal qtdRecebida;
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private Integer nrCaixas;
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private BigDecimal precoUnitario;
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private BigDecimal valorTotalItem;
    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class })
    private String observacao;
}
