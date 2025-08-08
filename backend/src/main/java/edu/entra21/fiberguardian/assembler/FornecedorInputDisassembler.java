package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.FornecedorDto;
import edu.entra21.fiberguardian.input.FornecedorInput;
import edu.entra21.fiberguardian.input.UsuarioaAdicionaNovoUsuarioInput;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FornecedorInputDisassembler
		extends EntityInputDisassembler<FornecedorInput, Fornecedor> {

	public FornecedorInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}
