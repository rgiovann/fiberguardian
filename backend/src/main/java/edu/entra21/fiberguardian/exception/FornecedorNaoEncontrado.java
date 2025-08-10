package edu.entra21.fiberguardian.exception;

import edu.entra21.fiberguardian.exception.exception.EntidadeNaoEncontradaException;

public class FornecedorNaoEncontrado  extends EntidadeNaoEncontradaException {

    public FornecedorNaoEncontrado(Long cnpj) {
        super(String.format("CNPJ de código %s não encontrado.", cnpj));
    }

    public FornecedorNaoEncontrado(String cnpj) {
        super("CNPJ : " + cnpj +" não existe.");
    }
}
