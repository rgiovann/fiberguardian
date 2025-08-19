package edu.entra21.fiberguardian.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemNotaFiscaSemCodigoNFInput {

    @NotBlank(message = "O código do produto é obrigatório")
    @NotNull(message = "O código do produto do item da nota é obrigatório.")
    @Size(max = 100, message = "O código do produto deve ter até 100 caracteres")
    private String codigoProduto;

    @Positive(message = "O valor quantidade recebida do item da nota  deve ser positivo ou maior que zero.")
    @NotNull(message = "O valor quantidade recebida  do item da nota é obrigatório.")
    private BigDecimal qtdRecebida;

    @NotNull(message = "O valor número de caixas do item da nota é obrigatório.")
    @Positive(message = "O número de caixas deve do item da nota ser maior que zero.")
    private Integer nrCaixas;

    @NotNull(message = "O valor unitário do item da nota é obrigatório.")
    @Positive(message = "O valor unitário do item da nota deve ser maior que zero.")
    @Digits(integer = 6, fraction = 2, message = "O valor total da nota deve ter no máximo 6 dígitos inteiros e 2 decimais.")
    private BigDecimal precoUnitario;

    private String observacao;


}
