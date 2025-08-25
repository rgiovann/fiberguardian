package edu.entra21.fiberguardian.service.query;

import edu.entra21.fiberguardian.model.NotaFiscal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotaFiscalQueryServiceImpl implements NotaFiscalQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<NotaFiscal> consultarNotas(NotaFiscalFilter filtro, Pageable pageable) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(NotaFiscal.class);
        var root = query.from(NotaFiscal.class);
        // para carregar as entidades (está lazy na entidade NotaFiscal)
        root.fetch("fornecedor", JoinType.LEFT);
        root.fetch("recebidoPor", JoinType.LEFT);

        var predicates = construirPredicados(builder, root, filtro);

        query.select(root).distinct(true);
        query.where(predicates.toArray(new Predicate[0]));

        // Aplica ordenação do Pageable
        if (pageable.getSort().isSorted()) {
            var orders = new ArrayList<Order>();
            pageable.getSort().forEach(sortOrder -> {
                if (sortOrder.isAscending()) {
                    orders.add(builder.asc(root.get(sortOrder.getProperty())));
                } else {
                    orders.add(builder.desc(root.get(sortOrder.getProperty())));
                }
            });
            query.orderBy(orders);
        }

        // Executa query principal com paginação
        TypedQuery<NotaFiscal> typedQuery = manager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<NotaFiscal> content = typedQuery.getResultList();

        // Query para contar total de elementos
        Long total = contarTotalElementos(builder, filtro);

        return new PageImpl<>(content, pageable, total);
    }

    private List<Predicate> construirPredicados(CriteriaBuilder builder, Root<NotaFiscal> root, NotaFiscalFilter filtro) {
        var predicates = new ArrayList<Predicate>();

        // Join para acessar produto via itens
        var joinItens = root.join("itens", JoinType.LEFT);
        var joinProduto = joinItens.join("produto", JoinType.LEFT);

        // Filtro por intervalo de datas
        if (filtro.getDataRecebimentoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataRecebimento"), filtro.getDataRecebimentoInicio()));
        }
        if (filtro.getDataRecebimentoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataRecebimento"), filtro.getDataRecebimentoFim()));
        }

        // Filtro por código da NF (LIKE)
        if (filtro.getCodigoNf() != null && !filtro.getCodigoNf().isBlank()) {
            predicates.add(builder.like(root.get("codigoNf"), "%" + filtro.getCodigoNf() + "%"));
        }

        // Filtro por fornecedor (cnpj)
        if (filtro.getFornecedorCnpj() != null && !filtro.getFornecedorCnpj().isBlank()) {
            predicates.add(builder.equal(root.get("fornecedor").get("cnpj"), filtro.getFornecedorCnpj()));
        }

        // Filtro por produto (codigo)
        if (filtro.getProdutoCodigo() != null && !filtro.getProdutoCodigo().isBlank()) {
            predicates.add(builder.equal(joinProduto.get("codigo"), filtro.getProdutoCodigo()));
        }

        return predicates;
    }

    private Long contarTotalElementos(CriteriaBuilder builder, NotaFiscalFilter filtro) {
        var countQuery = builder.createQuery(Long.class);
        var countRoot = countQuery.from(NotaFiscal.class);

        var predicates = construirPredicados(builder, countRoot, filtro);

        // Para count com DISTINCT, precisa contar as NotaFiscal distintas
        countQuery.select(builder.countDistinct(countRoot));
        countQuery.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(countQuery).getSingleResult();
    }
}

/*
@Repository
public class NotaFiscalQueryServiceImpl implements NotaFiscalQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<NotaFiscal> consultarNotas(NotaFiscalFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(NotaFiscal.class);
        var root = query.from(NotaFiscal.class);

        var predicates = new ArrayList<Predicate>();

        // Join para acessar produto via itens
        var joinItens = root.join("itens", JoinType.LEFT);
        var joinProduto = joinItens.join("produto", JoinType.LEFT);

        // Filtro por intervalo de datas
        if (filtro.getDataRecebimentoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataRecebimento"), filtro.getDataRecebimentoInicio()));
        }
        if (filtro.getDataRecebimentoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataRecebimento"), filtro.getDataRecebimentoFim()));
        }

        // Filtro por código da NF (LIKE)
        if (filtro.getCodigoNf() != null && !filtro.getCodigoNf().isBlank()) {
            predicates.add(builder.like(root.get("codigoNf"), "%" + filtro.getCodigoNf() + "%"));
        }

        // Filtro por fornecedor (cnpj)
        if (filtro.getFornecedorCnpj() != null && !filtro.getFornecedorCnpj().isBlank()) {
            predicates.add(builder.equal(root.get("fornecedor").get("cnpj"), filtro.getFornecedorCnpj()));
        }

        // Filtro por produto (codigo)
        if (filtro.getProdutoCodigo() != null && !filtro.getProdutoCodigo().isBlank()) {
            predicates.add(builder.equal(joinProduto.get("codigo"), filtro.getProdutoCodigo()));
        }

        query.select(root).distinct(true); // distinct evita duplicar notas quando há join com itens
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }
}
*/