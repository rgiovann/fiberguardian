package edu.entra21.fiberguardian.exception.exception;

public class FornecedorNaoEncontradoException extends EntidadeNaoEncontradaException {

    public FornecedorNaoEncontradoException(Long cnpj) {
        super(String.format("CNPJ de código %d não encontrado.", cnpj));
    }

    public FornecedorNaoEncontradoException(String cnpj) {
        super("CNPJ : " + cnpj +" não existe.");
    }
}
