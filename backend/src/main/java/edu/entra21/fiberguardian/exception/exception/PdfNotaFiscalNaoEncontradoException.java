package edu.entra21.fiberguardian.exception.exception;

import java.io.Serial;

public class PdfNotaFiscalNaoEncontradoException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PdfNotaFiscalNaoEncontradoException(String msg) {
        super(msg);
    }

    public PdfNotaFiscalNaoEncontradoException(Long notaFiscalId ) {
        // chamando o construtor anterior;
        this(String.format("Não existe uma Nota Fiscal com código %d", notaFiscalId));
    }
}
