package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //Optional<Produto> findByFornecedorCnpjAndCodigoProduto(String cnpj, String codigoProduto);

    void deleteByFornecedorCnpjAndCodigo(String cnpj, String codigoProduto);

    boolean existsByFornecedorCnpjAndCodigo(String cnpj, String codigoProduto);

    @EntityGraph(attributePaths = {"fornecedor"})
    Page<Produto> findByFornecedorCnpj( String cnpj, Pageable pageable);

    @EntityGraph(attributePaths = {"fornecedor"})
    List<Produto> findByFornecedorCnpj(@Param("cnpj") String cnpj);

    @Query("""
    SELECT p FROM Produto p 
    WHERE p.fornecedor.cnpj = :cnpj 
    AND (p.codigo = :codigo OR p.descricao = :descricao)
    """)
    Optional<Produto> findByCnpjAndCodigoOrDescricao(
            @Param("cnpj") String cnpj,
            @Param("codigo") String codigo,
            @Param("descricao") String descricao
    );

    @Query("SELECT p FROM Produto p JOIN FETCH p.fornecedor f " +
            "WHERE f.cnpj = :cnpj AND p.codigo = :codigo")
    Optional<Produto> findByFornecedorCnpjAndCodigoProduto(
            @Param("cnpj") String cnpj,
            @Param("codigo") String codigo
    );
}

