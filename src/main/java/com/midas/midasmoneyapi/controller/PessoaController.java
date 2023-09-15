package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.eventos.EventoRecursoCriado;
import com.midas.midasmoneyapi.model.Pessoa;
import com.midas.midasmoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar(){
        return ResponseEntity.ok(pessoaRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response){
        Pessoa p = pessoa;
        pessoaRepo.save(p);
        eventPublisher.publishEvent(new EventoRecursoCriado(this, response, p.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
}
