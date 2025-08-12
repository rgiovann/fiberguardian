package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "nota_fiscal",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_nota_fiscal_fornecedor_codigo_nf",
                        columnNames = {"fornecedor_id", "codigo_nf"}
                )
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "codigo_nf", nullable = false, length = 100)
    private String codigoNf;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fornecedor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_nota_fiscal_fornecedor")
    )
    private Fornecedor fornecedor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "recebido_por",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_nota_fiscal_usuario_recebeu")
    )
    private Usuario recebidoPor;

    //@DecimalMin(value = "0.00")
    @Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_recebimento", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataRecebimento;

    @CreatedBy
    @Column(name = "criado_por", updatable = false)
    private Long criadoPor;

    @LastModifiedBy
    @Column(name = "alterado_por")
    private Long alteradoPor;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_alteracao", columnDefinition = "datetime")
    private OffsetDateTime dataAlteracao;

}