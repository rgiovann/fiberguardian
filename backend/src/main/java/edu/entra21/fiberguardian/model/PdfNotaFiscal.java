package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "pdf_nota_fiscal")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PdfNotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    @Column(name="nota_fiscal_id")
    private Long Id;

    @OneToOne( fetch = FetchType.LAZY)
    @MapsId     // link Id da foto com Id do produto

    private NotaFiscal notaFiscal;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;


    public Long getNotaFiscalId()
    {
        if(this.getNotaFiscal() != null) {

            return this.getNotaFiscal().getId();
        }
        return null;
    }


}