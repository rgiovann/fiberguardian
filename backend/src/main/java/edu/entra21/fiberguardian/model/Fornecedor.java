package edu.entra21.fiberguardian.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "fornecedor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome_fornecedor", nullable = false, unique = true, length = 255)
    private String nomeFornecedor;

    @Column(name = "cnpj", nullable = false, unique = true, length = 45)
    private String cnpj;

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

