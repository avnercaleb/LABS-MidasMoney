package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.model.Categoria;
import com.midas.midasmoneyapi.repository.CategoriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepo catRepo;
    @GetMapping
    public List<Categoria> listar(){
        return catRepo.findAll();
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Categoria cat, UriComponentsBuilder uriBuilder){
        Categoria c = catRepo.save(cat);
        URI uri = uriBuilder.path("/categorias/{id}")
                .buildAndExpand(c.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
