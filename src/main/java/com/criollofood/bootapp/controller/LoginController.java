package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.domain.Permiso;
import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value= "/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping(value = "/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new Usuario());
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView signup(@Valid @ModelAttribute("user") Usuario user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (usuarioService.isUsuarioExists(user.getUsername())) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "*El username ingresado no esta disponible");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            Usuario signedUpUser = usuarioService.createUsuario(user);
            authWithoutPassword(signedUpUser);

            return new ModelAndView("redirect:/");
        }

        return modelAndView;
    }

    private void authWithoutPassword(Usuario user) {
        List<Permiso> privileges = user.getGrupos()
                .stream()
                .map(Grupo::getPermisos)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = privileges.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
