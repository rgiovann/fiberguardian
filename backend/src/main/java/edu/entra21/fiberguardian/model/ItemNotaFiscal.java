package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "nota_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_nota_item_unico_produto_por_nota", columnNames = {"nota_fiscal_id", "produto_id"})
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class ItemNotaFiscal {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_fiscal_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nota_item_nota"))
    private NotaFiscal notaFiscal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nota_item_produto"))
    private Produto produto;

    //@DecimalMin(value = "0.01", message = "A quantidade recebida deve ser maior que zero")
    @Column(name = "quantidade_recebida", precision = 15, scale = 2)
    private BigDecimal quantidadeRecebida;

    @NotNull
    //@Min(value = 0, message = "O número de caixas não pode ser negativo")
    @Column(name = "numero_caixas", nullable = false)
    private Integer numeroCaixas;

    @NotNull
    //@DecimalMin(value = "0.00", inclusive = true, message = "O valor do item não pode ser negativo")
    @Column(name = "valor_item", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorItem;

    //@DecimalMin(value = "0.00", inclusive = true, message = "O preço unitário não pode ser negativo")
    @Column(name = "preco_unitario", precision = 15, scale = 2)
    private BigDecimal precoUnitario;

    //@Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

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
