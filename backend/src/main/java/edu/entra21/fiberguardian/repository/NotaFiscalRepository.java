package edu.entra21.fiberguardian.repository;


import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal,Long>, NotaFiscalRepositoryQueries {

    @Query("SELECT pnf FROM PdfNotaFiscal pnf JOIN pnf.notaFiscal nf WHERE nf.id = :notaFiscalId")
    Optional<PdfNotaFiscal> findPdfNotaFiscalById(@Param("notaFiscalId") Long notaFiscalId);


}