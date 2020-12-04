package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.utils.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    private final AuthenticationFacade authenticationFacade;

    public HomeController(@Autowired AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value= "/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        if (authenticationFacade.hasAuthority("ADMINISTRADOR")) {
            modelAndView.setViewName("redirect:/reporteria");
        } else if (authenticationFacade.hasAuthority("RECEPCION")) {
            modelAndView.setViewName("redirect:/recepcion");
        } else if (authenticationFacade.hasAuthority("COCINA")) {
            modelAndView.setViewName("redirect:/cocina");
        } else {
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

    @GetMapping(value= "/index")
    public ModelAndView index(){
        return new ModelAndView("redirect:/");
    }

    @GetMapping(value= "/403")
    public ModelAndView forbidden(){
        return new ModelAndView("403");
    }
}
