package edu.entra21.fiberguardian.service.query;

import edu.entra21.fiberguardian.dto.LaboratorioListagemPagedDto;
import edu.entra21.fiberguardian.model.Laboratorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LaboratorioQueryService {

    /**
     * Busca linhas de laboratório de acordo com o filtro e paginação.
     *
     * @param filtro  filtros opcionais (datas, cnpj, produto, status, etc.)
     * @param pageable paginação e ordenação solicitadas
     * @return Page contendo os DTOs de listagem (conteúdo + metadados de paginação)
     */
    Page<LaboratorioListagemPagedDto> buscarLaudoPorFiltro(LaboratorioFilter filtro, Pageable pageable);
}