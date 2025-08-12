package edu.entra21.fiberguardian.exception.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ProdutoNaoEncontradoException(String cnpj, String codigoProduto) {
        super(String.format("Produto com código %s para fornecedor com CNPJ %s não foi encontrado.", codigoProduto, cnpj));
    }
}

