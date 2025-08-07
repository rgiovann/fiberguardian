package edu.entra21.fiberguardian.model;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "laboratorio",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_laboratorio_numero_lote", columnNames = {"cod_nota_fiscal", "numero_lote"})
        })
@EntityListeners(AuditingEntityListener.class)
public class Laboratorio {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_nota_fiscal", nullable = false,
            foreignKey = @ForeignKey(name = "fk_laboratorio_nota_fiscal"))
    private NotaFiscal notaFiscal;

    @Column(name = "numero_lote", nullable = false, length = 45)
    private String numeroLote;

    @Column(name = "cvm", nullable = false, precision = 15, scale = 2)
    private BigDecimal cvm;

    @Column(name = "pontos_finos", nullable = false)
    private Integer pontosFinos;

    @Column(name = "pontos_grossos", nullable = false)
    private Integer pontosGrossos;

    @Column(name = "neps", nullable = false)
    private Integer neps;

    @Column(name = "h_pilosidade", nullable = false, precision = 15, scale = 2)
    private BigDecimal pilosidade;

    @Column(name = "resistencia", nullable = false, precision = 15, scale = 2)
    private BigDecimal resistencia;

    @Column(name = "alongamento", nullable = false, precision = 15, scale = 2)
    private BigDecimal alongamento;

    @Column(name = "titulo_ne", nullable = false, precision = 15, scale = 2)
    private BigDecimal tituloNe;

    @Column(name = "torcao_t_m", nullable = false)
    private Integer torcaoTM;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusLaboratorio status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liberado_por", nullable = false,
            foreignKey = @ForeignKey(name = "fk_laboratorio_usuario_liberado"))
    private Usuario liberadoPor;

    @Column(name = "data_realizacao", nullable = false, columnDefinition = "date")
    private LocalDate dataRealizacao;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_alteracao", columnDefinition = "datetime")
    private OffsetDateTime dataAlteracao;

    @CreatedBy
    @Column(name = "criado_por", updatable = false)
    private Long criadoPor;

    @LastModifiedBy
    @Column(name = "alterado_por")
    private Long alteradoPor;
}
