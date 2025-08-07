package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalService {
    public NotaFiscal buscarOuFalhar(Long cursoId) {
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setId(1L);
        return notaFiscal;
    }
}
