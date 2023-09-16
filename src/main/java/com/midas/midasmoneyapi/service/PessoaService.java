package com.midas.midasmoneyapi.service;

import com.midas.midasmoneyapi.model.Pessoa;
import com.midas.midasmoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repo;

    public void definirStatusPessoa(@PathVariable @Valid Long id, @RequestBody Boolean status){
        Pessoa p = repo.findById(id).get();
        p.setAtivo(status);
        repo.save(p);
    }
}
