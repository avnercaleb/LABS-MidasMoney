package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.eventos.EventoRecursoCriado;
import com.midas.midasmoneyapi.model.Categoria;
import com.midas.midasmoneyapi.repository.CategoriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepo catRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @GetMapping
    public List<Categoria> listar(){
        return catRepo.findAll();
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid Categoria cat, HttpServletResponse response){
        Categoria c = catRepo.save(cat);
        eventPublisher.publishEvent(new EventoRecursoCriado(this, response, c.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id){
       return null;
    }
}
