package com.midas.midasmoneyapi.service;

import com.midas.midasmoneyapi.exceptionhandler.UsuarioInativoException;
import com.midas.midasmoneyapi.model.Lancamento;
import com.midas.midasmoneyapi.model.Pessoa;
import com.midas.midasmoneyapi.repository.LancamentoRepository;
import com.midas.midasmoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {
    @Autowired
    private PessoaRepository pessoaRepo;

    private LancamentoRepository lancRepo;

    public Lancamento cadastrar(Lancamento lancamento){
        Pessoa p = pessoaRepo.findById(lancamento.getPessoa().getId()).get();
        if(p == null || !p.getAtivo()){
            throw new UsuarioInativoException("Não e possivel realizar lançamento para usuarios inativos");
        }
        return lancRepo.save(lancamento);
    }

}
