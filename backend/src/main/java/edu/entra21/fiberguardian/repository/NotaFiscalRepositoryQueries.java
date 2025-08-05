package edu.entra21.fiberguardian.repository;

import edu.entra21.fiberguardian.model.PdfNotaFiscal;

public interface NotaFiscalRepositoryQueries {
    PdfNotaFiscal save(PdfNotaFiscal pdfNotaFiscal);

    void delete(PdfNotaFiscal foto);
}
