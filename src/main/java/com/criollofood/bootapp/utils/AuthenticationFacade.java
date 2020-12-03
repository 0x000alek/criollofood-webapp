package com.criollofood.bootapp.utils;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.domain.Usuario;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationFacade {

    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void auth(Usuario usuario) {
        List<GrantedAuthority> authorities = getUserAuthority(usuario.getGrupos());

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public List<GrantedAuthority> getUserAuthority(List<Grupo> grupos) {
        return grupos.stream()
                .map(g -> new SimpleGrantedAuthority(g.getName()))
                .collect(Collectors.toList());
    }

    public boolean hasAuthority(String role) {
        Authentication authentication = getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    public boolean hasAnyAuthority(String[] roles) {
        Authentication authentication = getAuthentication();
        for (String role : roles) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(role))) {
                return true;
            }
        }
        return false;
    }
}
