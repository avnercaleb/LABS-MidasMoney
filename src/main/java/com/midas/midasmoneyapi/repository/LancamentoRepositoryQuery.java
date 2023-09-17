package com.midas.midasmoneyapi.repository;

import com.midas.midasmoneyapi.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable page);
}
