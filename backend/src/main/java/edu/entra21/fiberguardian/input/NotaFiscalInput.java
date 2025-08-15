package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.CnpjNotInvalid;
import edu.entra21.fiberguardian.validation.EmailValido;
import edu.entra21.fiberguardian.validation.RecebimentoRecente;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Setter
@Getter
public class NotaFiscalInput {

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 100, message = "Nome deve ter até 100 caracteres")
	private String codigoNf;

	@NotBlank(message = "Cnpj é obrigatório")
	@CnpjNotInvalid
	private String cnpj;

	@EmailValido
	private String recebidoPor;

	@NotNull(message = "A data de recebimento é obrigatória.")
	@PastOrPresent(message = "A data de recebimento não pode ser futura.")
	@RecebimentoRecente(mesesMaximo = 6)
	LocalDate dataRecebimento;

	@NotNull(message = "O valor total da nota é obrigatório.")
	@Positive(message = "O valor total da nota deve ser maior que zero.")
	@Digits(integer = 9, fraction = 2, message = "O valor total da nota deve ter no máximo 9 dígitos inteiros e 2 decimais.")
	private BigDecimal valorTotal;

}