package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.model.Lancamento;
import com.midas.midasmoneyapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LacamentoController {
    @Autowired
    private LancamentoRepository repo;

    @GetMapping
    public ResponseEntity<List<Lancamento>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarLancamento(@PathVariable @Valid Long id){
        return ResponseEntity.ok(repo.findById(id).get());
    }
}
