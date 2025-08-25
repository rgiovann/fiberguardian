package edu.entra21.fiberguardian.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class NotaFiscalListagemPagedDto {

    private String codigoNf;
    private BigDecimal valorTotal;
    private LocalDate dataRecebimento;
    private String  cnpjFornecedor;
    private String nomeFornecedor;
    private String emailUsuario;
 }

