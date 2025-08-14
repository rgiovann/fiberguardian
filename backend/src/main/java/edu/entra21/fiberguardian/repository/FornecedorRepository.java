package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByCnpj(String cnpj);

    void deleteByCnpj(String cnpj);

    List<Fornecedor> findByNomeFornecedorContainingIgnoreCase(String nome);

    Optional<Fornecedor> findByNomeFornecedorIgnoreCase(String nomeFornecedor);

    @Query("SELECT COUNT(f) > 0 FROM Fornecedor f WHERE f.cnpj = :cnpj")
    boolean existsFornecedorByCnpj(@Param("cnpj") String cnpj);

    @Query("""
    SELECT f
    FROM Fornecedor f
    WHERE LOWER(f.nomeFornecedor) LIKE LOWER(CONCAT('%', :nomeParcial, '%'))
""")
    List<Fornecedor> findTop20ByNomeFornecedorContainingIgnoreCase(
            @Param("nomeParcial") String nomeParcial
    );

}
