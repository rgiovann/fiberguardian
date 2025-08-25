package edu.entra21.fiberguardian.service.query;

import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotaFiscalQueryService {
    //List<NotaFiscal> consultarNotas(NotaFiscalFilter filtro);
    Page<NotaFiscal> consultarNotas(NotaFiscalFilter filtro, Pageable pageable );
}
