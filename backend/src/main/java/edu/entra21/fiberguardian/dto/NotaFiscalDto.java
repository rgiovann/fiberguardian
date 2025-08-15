package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Setter
@Getter
public class NotaFiscalDto {

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            NotaFiscalView.Completo.class})
    private Long id;

    @JsonView({NotaFiscalView.NotafiscalRespostaDto.class,
            NotaFiscalView.Completo.class})
    private String codigoNf;

    @JsonView({ NotaFiscalView.Completo.class,
            NotaFiscalView.NotafiscalRespostaDto.class})
    private FornecedorDto fornecedor;

    @JsonView({ NotaFiscalView.Completo.class,
            NotaFiscalView.NotafiscalRespostaDto.class})
    private UsuarioDto recebidoPor;

    @JsonView({ NotaFiscalView.Completo.class,
            NotaFiscalView.NotafiscalRespostaDto.class})
    private BigDecimal valorTotal;

    @JsonView({ NotaFiscalView.Completo.class,
            NotaFiscalView.NotafiscalRespostaDto.class})
    private LocalDate dataRecebimento;
}
