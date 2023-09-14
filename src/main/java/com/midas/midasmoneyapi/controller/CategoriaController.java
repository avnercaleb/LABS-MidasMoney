package com.midas.midasmoneyapi.controller;

import com.midas.midasmoneyapi.model.Categoria;
import com.midas.midasmoneyapi.repository.CategoriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
