package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Setter
@Getter
public class NotaFiscalDto {

    @JsonView({NotaFiscalView.NFNomeFornecedorRecebidoPor.class,NotaFiscalView.Completo.class})
    private String codigoNf;
    @JsonView({ NotaFiscalView.Completo.class, NotaFiscalView.NFNomeFornecedorRecebidoPor.class})
    private FornecedorDto fornecedor;
    @JsonView({ NotaFiscalView.Completo.class,NotaFiscalView.NFNomeFornecedorRecebidoPor.class})
    private UsuarioDto recebidoPor;
    @JsonView({ NotaFiscalView.Completo.class})
    private BigDecimal valorTotal;
    @JsonView({ NotaFiscalView.Completo.class})
    private OffsetDateTime dataRecebimento;


}
