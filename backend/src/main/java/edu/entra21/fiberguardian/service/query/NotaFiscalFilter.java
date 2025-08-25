package edu.entra21.fiberguardian.service.query;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotaFiscalFilter {
    private LocalDate dataRecebimentoInicio;
    private LocalDate dataRecebimentoFim;
    private String codigoNf;
    private String fornecedorCnpj;
    private String produtoCodigo;
}