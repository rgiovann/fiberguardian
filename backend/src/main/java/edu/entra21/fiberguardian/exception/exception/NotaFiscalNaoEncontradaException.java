package edu.entra21.fiberguardian.exception.exception;

public class NotaFiscalNaoEncontradaException extends EntidadeNaoEncontradaException {

    public NotaFiscalNaoEncontradaException(Long nf) {
        super(String.format("Nota Fiscal de código %d não encontrado.", nf));
    }


    public NotaFiscalNaoEncontradaException(String nf) {
        super("Nota Fiscal : " + nf +" não existe.");
    }
}
