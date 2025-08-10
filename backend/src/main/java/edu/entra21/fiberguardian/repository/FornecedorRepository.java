package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.exception.FornecedorNaoEncontrado;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByCnpj(String cnpj);

    void deleteByCnpj(String cnpj);

    List<Fornecedor> findByNomeFornecedorContainingIgnoreCase(String nome);

    Optional<Fornecedor> findByNomeFornecedorIgnoreCase(String nomeFornecedor);


}
