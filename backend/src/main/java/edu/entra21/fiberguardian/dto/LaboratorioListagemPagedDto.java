package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.StatusLaboratorio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratorioListagemPagedDto {
    private Long id;
    private String numeroNf;
    private String cnpj;
    private String empresa;
    private String codigoProduto;
    private String descricao;
    private String numeroLote;
    private String emailEmitidoPor;
    private LocalDate dataRealizacao;
    private String observacoes;
    private StatusLaboratorio status;
}

