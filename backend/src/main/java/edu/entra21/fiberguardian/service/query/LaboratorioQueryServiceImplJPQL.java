package edu.entra21.fiberguardian.service.query;
import edu.entra21.fiberguardian.dto.LaboratorioListagemPagedDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class LaboratorioQueryServiceImplJPQL implements LaboratorioQueryService {

    @PersistenceContext
    private EntityManager em;
    private static final int DEFAULT_MAX_RESULTS = 20; // ou via @Value

    /**
     * Retorna uma Page de LaboratorioListagemPagedDto usando JPQL e constructor expression.
     * Usa count separado para total (pagination-safe).
     *
     * Atenção: a ordem e os tipos do constructor do DTO devem bater exatamente com a lista de seleção abaixo.
     */
    @Override
    public Page<LaboratorioListagemPagedDto> buscarLaudoPorFiltro(LaboratorioFilter filtro, Pageable pageable) {

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable não pode ser null para consultas paginadas");
        }

        String select =
                "select new edu.entra21.fiberguardian.dto.LaboratorioListagemPagedDto(" +
                        "  nf.codigoNf, f.cnpj, f.nomeFornecedor, p.codigo, p.descricao, l.numeroLote, u.email, l.dataRealizacao, l.observacaoLaudo, l.status" +
                        ")";

        String from =
                " from Laboratorio l " +
                        " join l.itemNotaFiscal it " +
                        " join it.notaFiscal nf " +
                        " join nf.fornecedor f " +
                        " join it.produto p " +
                        " left join l.liberadoPor u ";

        StringBuilder where = new StringBuilder(" where 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (filtro != null) {
            // normalize once
            String codigoNf = filtro.getCodigoNfTrimmedUpperCase();
            String cnpj = filtro.normalizedFornecedorCnpj();
            String codigoProduto = filtro.getCodigoProdutoTrimmedUpperCase();
            String usuarioEmail = filtro.getEmailEmitidoPorNormalizedLowerCase();
            // datas
            if (filtro.getDataRecebimentoInicio() != null) {
                where.append(" and nf.dataRecebimento >= :dataInicio");
                params.put("dataInicio", filtro.getDataRecebimentoInicio());
            }
            if (filtro.getDataRecebimentoFim() != null) {
                where.append(" and nf.dataRecebimento <= :dataFim");
                params.put("dataFim", filtro.getDataRecebimentoFim());
            }
            // codigo NF (LIKE)
            if (StringUtils.hasText(codigoNf)) {
                where.append(" and UPPER(nf.codigoNf) like :codigoNf");
                params.put("codigoNf", "%" + codigoNf + "%");
            }
            // fornecedor cnpj (exact)
            if (StringUtils.hasText(cnpj)) {
                where.append(" and f.cnpj = :cnpj");
                params.put("cnpj", cnpj);
            }
            // produto
            if (StringUtils.hasText(codigoProduto)) {
                where.append(" and UPPER(p.codigo) = :codigoProduto");
                params.put("codigoProduto", codigoProduto);
            }
            // laudo emitido por (email normalizado, case-insensitive)
            if (StringUtils.hasText(usuarioEmail)) {
                where.append(" and LOWER(u.email) = :usuarioEmail");
                params.put("usuarioEmail", usuarioEmail);
            }
            // status
            if (filtro.getStatus() != null) {
                where.append(" and l.status = :status");
                params.put("status", filtro.getStatus());
            }
        }

        String orderBy = buildOrderByFromPageable(pageable);
        String jpql = select + from + where.toString() + orderBy;

        log.debug("[FG] Executando query com filtros: {}", filtro);
        log.debug("[FG] JPQL gerado: {}", jpql);

        TypedQuery<LaboratorioListagemPagedDto> query = em.createQuery(jpql, LaboratorioListagemPagedDto.class);
        params.forEach(query::setParameter);

        // paginação
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<LaboratorioListagemPagedDto> content = query.getResultList();

        // count separado
        String countJpql = "select count(distinct l.id) " + from + where.toString();
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * Mapeamento controlado do Sort do Pageable para JPQL order by seguro.
     * Evita injeção de nomes de campos.
     */
    private String buildOrderByFromPageable(Pageable pageable) {
        if (pageable == null || !pageable.getSort().isSorted()) {
            return " order by nf.codigoNf asc ";
        }

        Map<String, String> allowed = Map.of(
                "notafiscal", "nf.codigoNf",
                "cnpj", "f.cnpj",
                "empresa", "f.nomeFornecedor",
                "codigo", "p.codigo",
                "dataEmissao", "l.dataRealizacao",
                "status", "l.status",
                "email", "u.email"
        );

        StringBuilder order = new StringBuilder(" order by ");
        List<String> parts = new ArrayList<>();

        pageable.getSort().forEach(orderItem -> {
            String prop = orderItem.getProperty();
            String mapped = allowed.get(prop);
            if (mapped != null) {
                parts.add(mapped + (orderItem.isAscending() ? " asc" : " desc"));
            }
        });

        if (parts.isEmpty()) {
            return " order by nf.codigoNf asc ";
        }
        order.append(String.join(", ", parts));
        return order.toString();
    }
}
