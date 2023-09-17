package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.eventos.EventoRecursoCriado;
import com.midas.midasmoneyapi.model.Lancamento;
import com.midas.midasmoneyapi.repository.LancamentoFilter;
import com.midas.midasmoneyapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LacamentoController {
    @Autowired
    private LancamentoRepository repo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<Page<Lancamento>> pesquisar(LancamentoFilter filter, Pageable page) {
        return ResponseEntity.ok(repo.filtrar(filter, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarLancamento(@PathVariable @Valid Long id){
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Lancamento> cadastrar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response){
        Lancamento l = lancamento;
        repo.save(l);
        eventPublisher.publishEvent(new EventoRecursoCriado(this, response, l.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(l);
    }
}
