package edu.entra21.fiberguardian.input;

import edu.entra21.fiberguardian.validation.CnpjNotInvalid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoLoteInput {

    @NotBlank(message = "Cnpj é obrigatório")
    @CnpjNotInvalid
    private String fornecedorCnpj;

    @NotEmpty(message = "Lista de produtos não pode ser vazia")
    @Valid
    private List<ProdutoInput> produtos;
}
