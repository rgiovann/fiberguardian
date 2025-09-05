package edu.entra21.fiberguardian.assembler;

import edu.entra21.fiberguardian.configuration.Mapper;
import edu.entra21.fiberguardian.dto.LaboratorioListagemPagedDto;
import edu.entra21.fiberguardian.dto.NotaFiscalListagemPagedDto;
import edu.entra21.fiberguardian.model.Laboratorio;
import edu.entra21.fiberguardian.model.NotaFiscal;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioListagemPagedDtoAssembler extends EntitytDtoAssembler<LaboratorioListagemPagedDto, Laboratorio> {

    public LaboratorioListagemPagedDtoAssembler(Mapper mapper) {
        super(mapper);
    }

}
