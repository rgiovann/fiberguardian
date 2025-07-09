package edu.entra21.fiberguardian.exception.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public n(Long codigoPedidoId) {
        super(String.format("Usuário de código %s não encontrada.",codigoPedidoId) );
    }



}
