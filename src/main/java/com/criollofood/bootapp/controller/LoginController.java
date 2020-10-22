package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping(value="/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new Usuario());
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewUser(@Valid @ModelAttribute("user") Usuario user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        boolean userExists = usuarioService.isUsuarioExists(user.getUsername());
        LOGGER.info("user exists? " + userExists);
        if (userExists) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "*El username ingresado no esta disponible");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            usuarioService.createUsuario(user);
            modelAndView.addObject("successMessage", "Te has registrado con éxito");
            modelAndView.addObject("user", new Usuario());
            modelAndView.setViewName("signup");

        }
        return modelAndView;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = usuarioService.findByUsername(auth.getName());
        modelAndView.addObject("username", "Bienvenido/a " + user.getUsername() + " (" + user.getEmail() + ")");
        modelAndView.addObject("message","El contenido de esta página es sólo para usuarios registrados");
        modelAndView.setViewName("home");

        return modelAndView;
    }

}
