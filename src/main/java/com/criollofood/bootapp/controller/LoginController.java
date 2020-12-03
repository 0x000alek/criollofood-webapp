package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.service.UsuarioService;
import com.criollofood.bootapp.utils.AuthenticationFacade;
import com.criollofood.bootapp.utils.Pbkdf2Sha256PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final AuthenticationFacade authenticationFacade;
    private final UsuarioService usuarioService;
    private final Pbkdf2Sha256PasswordEncoder passwordEncoder;

    public LoginController(@Autowired AuthenticationFacade authenticationFacade,
                           @Autowired UsuarioService usuarioService,
                           @Autowired Pbkdf2Sha256PasswordEncoder passwordEncoder) {
        this.authenticationFacade = authenticationFacade;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuario", new Usuario());
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@Valid @ModelAttribute Usuario usuario,
                              BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login?error=true");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            String password = usuario.getPassword();
            usuario = usuarioService.findByUsername(usuario.getUsername());
            if (Objects.isNull(usuario) || !passwordEncoder.checkPassword(password, usuario.getPassword())) {
                return modelAndView;
            }

            authenticationFacade.auth(usuario);
            usuarioService.updateLastLogin(usuario.getId());

            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
}
