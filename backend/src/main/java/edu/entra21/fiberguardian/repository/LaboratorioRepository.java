package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    @Query(value = """
        SELECT COUNT(*)
        FROM laboratorio l
        JOIN nota_item ni ON l.item_nota_fiscal_id = ni.id
        WHERE ni.nota_fiscal_id = :notaFiscalId
        """, nativeQuery = true)
    long countByNotaFiscalId(@Param("notaFiscalId") Long notaFiscalId);

}