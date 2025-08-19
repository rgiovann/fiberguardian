package edu.entra21.fiberguardian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.entra21.fiberguardian.jacksonview.NotaFiscalView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfNotaFiscalDto {

    @JsonView({
            NotaFiscalView.NotafiscalRespostaDto.class})
    private String nomeArquivo;
    @JsonView({
            NotaFiscalView.NotafiscalRespostaDto.class})
    private String descricao;
    @JsonView({
            NotaFiscalView.NotafiscalRespostaDto.class})
    private String contentType;
    @JsonView({
            NotaFiscalView.NotafiscalRespostaDto.class})
    private Long tamanho;
}
