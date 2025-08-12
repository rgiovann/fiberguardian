package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.input.ProdutoInput;
import edu.entra21.fiberguardian.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler
        extends EntityInputDisassembler<ProdutoInput, Produto> {

    public ProdutoInputDisassembler(Mapper mapper) {
        super(mapper);
    }

}