package com.midas.midasmoneyapi.repository;

import com.midas.midasmoneyapi.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        criarResticoesPaginacao(query, page);
        return new PageImpl<>(query.getResultList(), page, total(filter));
    }

    private void criarResticoesPaginacao(TypedQuery<Lancamento> query, Pageable page){
        int paginaAtual = page.getPageNumber();
        int totalResgistoPorPagina = page.getPageSize();
        int primeiroRegistro = paginaAtual * totalResgistoPorPagina;

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(totalResgistoPorPagina);
    }

    private Long total(LancamentoFilter filter){
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root){

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(filter.getDescricao())){
            predicates.add(builder.like(
                    builder.lower(root.get("descricao")), "%" + filter.getDescricao().toLowerCase() + "%"
            ));
        }

        if(filter.getDataVencimentoDe() != null){
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataVencimento"), filter.getDataVencimentoDe()
            ));
        }

        if(filter.getDataVencimentoAte() != null){
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataVencimento"), filter.getDataVencimentoAte()
            ));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
