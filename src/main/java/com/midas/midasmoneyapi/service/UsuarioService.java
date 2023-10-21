package com.midas.midasmoneyapi.service;

import com.midas.midasmoneyapi.model.Usuario;
import com.midas.midasmoneyapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;

    public Usuario cadastrar(Usuario usuario) {
        return repo.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }
}
