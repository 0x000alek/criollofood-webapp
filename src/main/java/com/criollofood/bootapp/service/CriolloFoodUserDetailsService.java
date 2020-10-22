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

@Service
public class CriolloFoodUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public CriolloFoodUserDetailsService(@Autowired UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);
        return buildUserForAuthentication(usuario, getUserAuthority(usuario.getRol()));
    }

    private List<GrantedAuthority> getUserAuthority(Grupo grupoUsuario) {
        Set<GrantedAuthority> roles = Collections.singleton(new SimpleGrantedAuthority(grupoUsuario.getName()));
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);
    }

}
