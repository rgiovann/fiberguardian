package edu.entra21.fiberguardian.exception.exception;

public class UsuarioNaoEncotradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncotradoException(String codigoPedidoId) {
        super(String.format("Usuário de código %s não encontrada.",codigoPedidoId) );
    }



}
