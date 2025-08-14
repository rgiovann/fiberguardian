package edu.entra21.fiberguardian.repository;


import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal,Long>, NotaFiscalRepositoryQueries {

    @Query("SELECT pnf FROM PdfNotaFiscal pnf JOIN pnf.notaFiscal nf WHERE nf.id = :notaFiscalId")
    Optional<PdfNotaFiscal> findPdfNotaFiscalById(@Param("notaFiscalId") Long notaFiscalId);

    Optional<NotaFiscal> findNotaFiscalById(@Param("notaFiscalId") Long notaFiscalId);


    /**
     * A query formada desse jeito (join fetch)
     * resolve todos os erros de ModelMapper ao mapear Fornecedor e Usuario.
     * Mantém seu código limpo, simples e seguro.
     * Permite usar o DTOAssembler normalmente, sem condicional ou TypeMap extra.
     * @param codigoParcialNf
     * @return
     *  usando Text Blocks (""")
     */
    @Query("""
    SELECT n
    FROM NotaFiscal n
    JOIN FETCH n.fornecedor f
    JOIN FETCH n.recebidoPor u
    WHERE LOWER(n.codigoNf) LIKE LOWER(CONCAT('%', :codigoParcialNf, '%'))
""")
    List<NotaFiscal> findTop20ByCodigoNfContainingIgnoreCase(
            @Param("codigoParcialNf") String codigoParcialNf
    );



    @EntityGraph(attributePaths = {"fornecedor"})
    List<NotaFiscal> findTop20ByFornecedorNomeFornecedorContainingIgnoreCase(String nomeParcialFornecedor);

    // Buscar notas fiscais pelo nome do usuário que recebeu (autocomplete)
    @EntityGraph(attributePaths = {"recebidoPor"})
    List<NotaFiscal> findTop20ByRecebidoPorNomeContainingIgnoreCase(String nomeParcialUsuario);
}