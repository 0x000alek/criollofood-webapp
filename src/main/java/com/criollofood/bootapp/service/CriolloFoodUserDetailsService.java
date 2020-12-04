package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CriolloFoodUserDetailsService implements UserDetailsService {
    private final UsuarioService usuarioService;

    public CriolloFoodUserDetailsService(@Autowired UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);
        return buildUserForAuthentication(usuario, getUserAuthority(usuario.getGrupos()));
    }

    private List<GrantedAuthority> getUserAuthority(List<Grupo> grupos) {
        return grupos.stream()
                .map(g -> new SimpleGrantedAuthority(g.getName()))
                .collect(Collectors.toList());
    }

    private UserDetails buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
        return new User(user.getEmail(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
