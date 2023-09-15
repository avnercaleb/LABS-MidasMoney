package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.model.Pessoa;
import com.midas.midasmoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepo;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar(){
        return ResponseEntity.ok(pessoaRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@RequestBody @Valid Pessoa pessoa, UriComponentsBuilder uriBuilder){
        Pessoa p = pessoa;
        pessoaRepo.save(p);
        URI uri = uriBuilder.path("/pessoas/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(uri).body(p);
    }
}
