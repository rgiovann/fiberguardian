package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.model.StatusLaboratorio;
import edu.entra21.fiberguardian.validation.CnpjNotInvalid;
import edu.entra21.fiberguardian.validation.EmailValido;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para cadastro de Laboratório.
 *
 * Observações:
 * - Usa campos simples (String/BigDecimal/Integer) em vez de objetos complexos.
 * - Evita expor entidades JPA diretamente.
 * - Campos anotados com validações Bean Validation para segurança de dados.
 */
@Setter
@Getter
public class LaboratorioInput {

    @NotBlank(message = "O CNPJ do fornecedor é obrigatório")
    @CnpjNotInvalid
    private String cnpj;

    @NotBlank(message = "O código da nota fiscal é obrigatório")
    private String codigoNf;

    @NotBlank(message = "O código do produto é obrigatório")
    private String codProduto;

    @NotBlank(message = "O número do lote é obrigatório")
    @Size(max = 45, message = "O número do lote deve ter no máximo 45 caracteres")
    private String numeroLote;

    @NotNull(message = "O CVM é obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "O CVM deve ser positivo")
    private BigDecimal cvm;

    @NotNull(message = "Pontos finos são obrigatórios")
    private Integer pontosFinos;

    @NotNull(message = "Pontos grossos são obrigatórios")
    private Integer pontosGrossos;

    @NotNull(message = "Neps é obrigatório")
    private Integer neps;

    @NotNull(message = "A pilosidade é obrigatória")
    private BigDecimal pilosidade;

    @NotNull(message = "A resistência é obrigatória")
    private BigDecimal resistencia;

    @NotNull(message = "O alongamento é obrigatório")
    private BigDecimal alongamento;

    @NotNull(message = "O título Ne é obrigatório")
    private BigDecimal tituloNe;

    @NotNull(message = "A torção TM é obrigatória")
    private Integer torcaoTm;

    @NotNull(message = "O status é obrigatório")
    private StatusLaboratorio status;

    @Size(max = 255, message = "O número do lote deve ter no máximo 255 caracteres")
    private String observacaoLaudo;

    @NotBlank(message = "O e-mail do responsável pela liberação é obrigatório")
    @EmailValido
    private String emailLaudoLab;

    @NotNull(message = "A data de realização é obrigatória")
    @PastOrPresent(message = "A data de realização não pode ser futura")
    private LocalDate dataRealizacao;
}
