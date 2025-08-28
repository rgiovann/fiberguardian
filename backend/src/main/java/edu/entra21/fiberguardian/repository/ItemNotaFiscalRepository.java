package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ItemNotaFiscalRepository extends JpaRepository<ItemNotaFiscal, Long> {

    @Query("SELECT i FROM ItemNotaFiscal i JOIN FETCH i.produto WHERE i.notaFiscal.id = :notaFiscalId")
    List<ItemNotaFiscal> findByNotaFiscalId(@Param("notaFiscalId") Long notaFiscalId);

}

