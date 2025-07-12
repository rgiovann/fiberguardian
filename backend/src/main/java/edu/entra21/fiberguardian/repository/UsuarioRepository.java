package edu.entra21.fiberguardian.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.entra21.fiberguardian.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	boolean existsByEmail(String email);

	// verifica se o email já existe
	// @Query("from Usuario u where u.email = :email and u.id <> :id")
	// Optional<Usuario> findByEmailAndIdNot(@Param("email") String email,
	// @Param("id") Long id);
}
