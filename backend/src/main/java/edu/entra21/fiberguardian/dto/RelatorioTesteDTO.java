package edu.entra21.fiberguardian.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatorioTesteDTO {
    private String numeroNf;
    private String cnpj;
    private String empresa;
    private String codigoProduto;
    private String descricao;
    private String numeroLote;
    private String emailEmitidoPor;
    private LocalDate dataRealizacao;
    private String observacoes;
    private String status;
    private BigDecimal cvm;
    private Integer pontosFinos;
    private Integer pontosGrossos;
    private Integer neps;
    private BigDecimal pilosidade;
    private BigDecimal resistencia;
    private BigDecimal alongamento;
    private BigDecimal tituloNe;
    private Integer torcaoTm;

    // Construtor padrão
    public RelatorioTesteDTO() {}

    // Método utilitário para data formatada (usado no template)
    public String getDataRealizacaoFormatada() {
        return dataRealizacao != null ?
                dataRealizacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
    }

    // Getters e Setters (gere via IDE)
    public String getNumeroNf() { return numeroNf; }
    public void setNumeroNf(String numeroNf) { this.numeroNf = numeroNf; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }

    public String getEmailEmitidoPor() { return emailEmitidoPor; }
    public void setEmailEmitidoPor(String emailEmitidoPor) { this.emailEmitidoPor = emailEmitidoPor; }

    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public void setDataRealizacao(LocalDate dataRealizacao) { this.dataRealizacao = dataRealizacao; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getCvm() { return cvm; }
    public void setCvm(BigDecimal cvm) { this.cvm = cvm; }

    public Integer getPontosFinos() { return pontosFinos; }
    public void setPontosFinos(Integer pontosFinos) { this.pontosFinos = pontosFinos; }

    public Integer getPontosGrossos() { return pontosGrossos; }
    public void setPontosGrossos(Integer pontosGrossos) { this.pontosGrossos = pontosGrossos; }

    public Integer getNeps() { return neps; }
    public void setNeps(Integer neps) { this.neps = neps; }

    public BigDecimal getPilosidade() { return pilosidade; }
    public void setPilosidade(BigDecimal pilosidade) { this.pilosidade = pilosidade; }

    public BigDecimal getResistencia() { return resistencia; }
    public void setResistencia(BigDecimal resistencia) { this.resistencia = resistencia; }

    public BigDecimal getAlongamento() { return alongamento; }
    public void setAlongamento(BigDecimal alongamento) { this.alongamento = alongamento; }

    public BigDecimal getTituloNe() { return tituloNe; }
    public void setTituloNe(BigDecimal tituloNe) { this.tituloNe = tituloNe; }

    public Integer getTorcaoTm() { return torcaoTm; }
    public void setTorcaoTm(Integer torcaoTm) { this.torcaoTm = torcaoTm; }
}