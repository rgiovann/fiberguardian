package edu.entra21.fiberguardian.model;

import java.time.OffsetDateTime;

import edu.entra21.fiberguardian.model.converter.LowerCaseConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)

public class Usuario {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, unique = true)
	private String nome;

	@Column(name = "email", nullable = false, unique = true)
	@Convert(converter =  LowerCaseConverter.class)
	private String email;

	@Column(name = "senha", nullable = false)
	@ToString.Exclude
	@JsonIgnore
	private String senha;

	@Column(name = "ativo", nullable = false)
	private Boolean ativo = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = Role.USUARIO;

	@Enumerated(EnumType.STRING)
	@Column(name = "turno", nullable = false)
	private Turno turno = Turno.GERAL;

	@Enumerated(EnumType.STRING)
	@Column(name = "setor", nullable = false)
	private Setor setor = Setor.FIACAO;

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

	@Converter
	public class EmailConverter implements AttributeConverter<String, String> {
		@Override
		public String convertToDatabaseColumn(String attribute) {
			return attribute == null ? null : attribute.trim().toLowerCase();
		}

		@Override
		public String convertToEntityAttribute(String dbData) {
			return dbData; // já normalizado no DB
		}
	}

}
