package com.midas.midasmoneyapi.security;

import com.midas.midasmoneyapi.exceptionhandler.UsuarioNaoEncontradoException;
import com.midas.midasmoneyapi.model.Usuario;
import com.midas.midasmoneyapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = repo.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        return new User(email, usuario.getSenha(), getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {

        Set<SimpleGrantedAuthority> auths = new HashSet<>();
        usuario.getPermissoes().forEach(p -> auths.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
        return auths;
    }
}
