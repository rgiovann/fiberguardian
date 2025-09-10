package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.CnpjNotInvalid;
import edu.entra21.fiberguardian.validation.EmailValido;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class LaboratorioRelatorioInput {
    // body do json
    @NotBlank(message = "O código da nota fiscal é obrigatório")
    private String numeroNf;
    @NotBlank(message = "O CNPJ do fornecedor é obrigatório")
    @CnpjNotInvalid
    private String cnpj;
    private String empresa;
    @NotBlank(message = "O código do produto é obrigatório")
    private String codigoProduto;
    @NotBlank(message = "A descrição do produto é obrigatório")
    private String descricao;
    @NotBlank(message = "O número do lote é obrigatório")
    @Size(max = 45, message = "O número do lote deve ter no máximo 45 caracteres")
    private String numeroLote;
    @NotBlank(message = "O e-mail do responsável pela liberação é obrigatório")
    @EmailValido
    private String emailEmitidoPor;
    @NotNull(message = "A data de recebimento é obrigatória.")
    @PastOrPresent(message = "A data de recebimento não pode ser futura.")
    private LocalDate dataRealizacaoNaFormatado;
    private String observacoes;
    @Pattern(regexp = "APROVADO|REPROVADO", message = "Status deve ser APROVADO ou REPROVADO")
    private String status;

   // virao da query da entidade
    private BigDecimal cvm;
    private Integer pontosFinos;
    private Integer pontosGrossos;
    private Integer neps;
    private BigDecimal pilosidade;
    private BigDecimal resistencia;
    private BigDecimal alongamento;
    private BigDecimal tituloNe;
    private Integer torcaoTm;

    public String getDataRealizacao() {
        return dataRealizacaoNaFormatado != null
                ? dataRealizacaoNaFormatado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "";
    }
}

