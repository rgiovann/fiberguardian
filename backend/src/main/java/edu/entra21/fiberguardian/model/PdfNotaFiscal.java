package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@Table(name = "pdf_nota_fiscal")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class PdfNotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    @Column(name="nota_fiscal_id")
    private Long id;

    @OneToOne( fetch = FetchType.LAZY)
    @MapsId     // link Id da foto com Id do produto

    private NotaFiscal notaFiscal;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

    @CreationTimestamp
    @Column(name = "data_cadastro",nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_alteracao", nullable = true, columnDefinition = "datetime")
    private OffsetDateTime dataAlteracao;

    @CreatedBy
    @Column(name = "criado_por", nullable = true, updatable = false)
    private Long criadoPor;

    @LastModifiedBy
    @Column(name = "alterado_por", nullable = true)
    private Long alteradoPor;

    public Long getNotaFiscalId()
    {
        if(this.getNotaFiscal() != null) {

            return this.getNotaFiscal().getId();
        }
        return null;
    }


}