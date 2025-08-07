package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "engenharia",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_teste_lab", columnNames = "teste_lab")
        })
@EntityListeners(AuditingEntityListener.class)
public class Engenharia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engenheiro", nullable = false,
            foreignKey = @ForeignKey(name = "fk_engenharia_usuario_engenharia"))
    private Usuario engenheiro;

    @Column(name = "teste_amostra_fisica_tecelagem", nullable = false)
    private Boolean testeAmostraFisicaTecelagem = false;

    @Column(name = "teste_acabamento", nullable = false)
    private Boolean testeAcabamento = false;

    @Column(name = "restricao_uso", nullable = false, length = 250)
    private String restricaoUso;

    @Column(name = "restricao_total", nullable = false)
    private Boolean restricaoTotal = false;

    @Column(name = "observacao", length = 500)
    private String observacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teste_lab", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_engenharia_teste_laboratorio"))
    private Laboratorio testeLaboratorio;

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

