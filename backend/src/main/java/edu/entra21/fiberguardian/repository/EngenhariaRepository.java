package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.Engenharia;
import edu.entra21.fiberguardian.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngenhariaRepository extends JpaRepository<Engenharia, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
            "FROM Engenharia e WHERE e.testeLaboratorio.id = :labId")
    boolean existsByTesteLaboratorioId(@Param("labId") Long labId);

}