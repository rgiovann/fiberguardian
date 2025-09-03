package edu.entra21.fiberguardian.exception.exception;

public class ItemNotaFiscalNaoEncontradoException extends EntidadeNaoEncontradaException {

//    public ItemNotaFiscalNaoEncontradoException(Long nf) {
//        super(String.format("Nota Fiscal de código %d não encontrado.", nf));
//    }

    public ItemNotaFiscalNaoEncontradoException(Long codProduto, Long codNf) {
        super("Item Nota fiscal produto id " + codProduto +" não existe para nota fiscal id" + codNf + "." );
    }

}
