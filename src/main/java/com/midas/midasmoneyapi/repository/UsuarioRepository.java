package com.midas.midasmoneyapi.repository;

import com.midas.midasmoneyapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
