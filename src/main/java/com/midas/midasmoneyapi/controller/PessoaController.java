package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.eventos.EventoRecursoCriado;
import com.midas.midasmoneyapi.model.Pessoa;
import com.midas.midasmoneyapi.repository.PessoaRepository;
import com.midas.midasmoneyapi.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private PessoaService service;

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

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoa(@PathVariable @Valid Long id){
        Pessoa p = pessoaRepo.findById(id).get();
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPessoa(@PathVariable Long id){
        pessoaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable @Valid Long id, @RequestBody @Valid Pessoa pessoa){
        Pessoa p = pessoaRepo.findById(id).get();
        BeanUtils.copyProperties(pessoa, p, "id");
        return ResponseEntity.ok().body(pessoaRepo.save(p));
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void definirStatusPessoa(@PathVariable @Valid Long id, @RequestBody Boolean status){
        service.definirStatusPessoa(id, status);
    }
}
