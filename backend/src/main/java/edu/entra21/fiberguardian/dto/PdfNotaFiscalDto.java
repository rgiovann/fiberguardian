package edu.entra21.fiberguardian.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfNotaFiscalDto {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
