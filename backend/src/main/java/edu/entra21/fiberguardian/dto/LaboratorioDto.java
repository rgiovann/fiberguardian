package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.StatusLaboratorio;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LaboratorioDto {

    private Long id;

    private String cnpj;
    private String codigoNf;
    private String codProduto;
    private String numeroLote;
    private BigDecimal cvm;
    private Integer pontosFinos;
    private Integer pontosGrossos;
    private Integer neps;
    private BigDecimal pilosidade;
    private BigDecimal resistencia;
    private BigDecimal alongamento;
    private BigDecimal tituloNe;
    private Integer torcaoTM;
    private StatusLaboratorio status;
    private LocalDate dataRealizacao;
    private String emailLaudoLab;

 }

