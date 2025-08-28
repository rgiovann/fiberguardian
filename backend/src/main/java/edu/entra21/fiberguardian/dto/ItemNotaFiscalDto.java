package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.ItemNotaFiscalView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemNotaFiscalDto {

   @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
               ItemNotaFiscalView.ItemNotaFiscalListDto.class})
   private ProdutoItemNotaFiscalDto produto;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
               ItemNotaFiscalView.ItemNotaFiscalListDto.class})
    private BigDecimal qtdRecebida;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            ItemNotaFiscalView.ItemNotaFiscalListDto.class })
    private Integer nrCaixas;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            ItemNotaFiscalView.ItemNotaFiscalListDto.class })
    private BigDecimal precoUnitario;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            ItemNotaFiscalView.ItemNotaFiscalListDto.class })
    private BigDecimal valorTotalItem;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            ItemNotaFiscalView.ItemNotaFiscalListDto.class })
    private String observacao;
}
