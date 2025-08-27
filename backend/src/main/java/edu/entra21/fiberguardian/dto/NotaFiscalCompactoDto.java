package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class NotaFiscalCompactoDto {
    @JsonView({NotaFiscalView.NotafiscalCompactoDto.class})
    private String codigoNf;
    @JsonView({NotaFiscalView.NotafiscalCompactoDto.class})
    private FornecedorDto fornecedor;
    @JsonView({NotaFiscalView.NotafiscalCompactoDto.class})
    private BigDecimal valorTotal;
    @JsonView({NotaFiscalView.NotafiscalCompactoDto.class})
    private LocalDate dataRecebimento;
}
