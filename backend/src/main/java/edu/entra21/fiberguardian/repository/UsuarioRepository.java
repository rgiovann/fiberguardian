package edu.entra21.fiberguardian.repository;

import java.util.List;
import java.util.Optional;

import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.entra21.fiberguardian.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	boolean existsByEmail(String email);

	List<Usuario> findBySetor(Setor setor);

	@Query("""
    SELECT u
    FROM Usuario u
    WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nomeParcial, '%'))
""")
	List<Usuario> findTop20ByNomeContainingIgnoreCase(
			@Param("nomeParcial") String nomeParcial
	);
}
