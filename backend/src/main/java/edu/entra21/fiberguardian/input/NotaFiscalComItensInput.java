package edu.entra21.fiberguardian.input;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotaFiscalComItensInput {
    @Valid
    private NotaFiscalInput nota;
    @Valid
    private List<ItemNotaFiscaSemCodigoNFInput> itens;
}