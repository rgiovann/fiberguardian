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
@Table(name = "engenharia")
@EntityListeners(AuditingEntityListener.class)

public class Engenharia {

    // -------------------------------------------------------------------------
    // Identidade
    // -------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // -------------------------------------------------------------------------
    // Vínculo com laudo laboratorial reprovado
    // Regras validadas na camada service antes do INSERT:
    //   1. laboratorio.decisao  = 'REPROVADO'
    //   2. laboratorio.situacao = 'ATIVO'
    //   3. Não existe outro engenharia ATIVO para o mesmo laudo_laboratorio_id
    // -------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "laudo_laboratorio_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_eng_laudo_laboratorio")
    )
    private Laboratorio laudoLaboratorio;

    // -------------------------------------------------------------------------
    // Responsabilidade técnica
    // -------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "engenheiro",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_eng_engenheiro")
    )
    private Usuario engenheiro;

    // -------------------------------------------------------------------------
    // Testes complementares
    // -------------------------------------------------------------------------
    @Column(name = "teste_amostra_fisica_tecelagem", nullable = false)
    private Boolean testeAmostraFisicaTecelagem = false;

    @Column(name = "teste_acabamento", nullable = false)
    private Boolean testeAcabamento = false;

    // -------------------------------------------------------------------------
    // Decisão técnica
    // APROVADO_PARCIAL → restricaoUso obrigatório (validado no service)
    // REPROVADO        → restricaoUso dispensável
    // -------------------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "decisao", nullable = false, length = 20)
    private Decisao decisao;

    @Column(name = "restricao_uso", length = 250)
    private String restricaoUso;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    // -------------------------------------------------------------------------
    // Datas
    // -------------------------------------------------------------------------
    @Column(name = "data_realizacao", nullable = false, columnDefinition = "date")
    private LocalDate dataRealizacao;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_alteracao", columnDefinition = "datetime")
    private OffsetDateTime dataAlteracao;

    // -------------------------------------------------------------------------
    // Auditoria
    // -------------------------------------------------------------------------
    @CreatedBy
    @Column(name = "criado_por", updatable = false)
    private Long criadoPor;

    @LastModifiedBy
    @Column(name = "alterado_por")
    private Long alteradoPor;

    // -------------------------------------------------------------------------
    // Ciclo de vida do documento
    // -------------------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false, length = 20)
    private Situacao situacao = Situacao.ATIVO;

    // Sem FK — o laudo substituto ainda não existe no momento do INSERT
    // Preenchido na mesma transação após INSERT do laudo novo
    @Column(name = "substituido_por")
    private Long substituidoPor;

    // Com FK — aponta para o passado, o registro já existe
    // OneToOne: um laudo substitui exatamente um outro, nunca dois
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "substitui",
            foreignKey = @ForeignKey(name = "fk_eng_substitui")
    )
    private Engenharia substitui;

    @Column(name = "motivo_substituicao", length = 255)
    private String motivoSubstituicao;
}

