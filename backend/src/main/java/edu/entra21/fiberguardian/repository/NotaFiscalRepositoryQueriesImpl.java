package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotaFiscalRepositoryQueriesImpl implements NotaFiscalRepositoryQueries{
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public PdfNotaFiscal save(PdfNotaFiscal pdfNotaFiscal) {
        return this.manager.merge(pdfNotaFiscal);
    }

//    @Override
//    @Transactional
//    public void delete(PdfNotaFiscal pdfNotaFiscal) {
//        this.manager.remove(pdfNotaFiscal);
//    }

    @Override
    @Transactional
    public void delete(PdfNotaFiscal pdfNotaFiscal) {
        PdfNotaFiscal managed = this.manager.find(PdfNotaFiscal.class, pdfNotaFiscal.getId());
        if (managed != null) {
            this.manager.remove(managed);
        }
    }
}
