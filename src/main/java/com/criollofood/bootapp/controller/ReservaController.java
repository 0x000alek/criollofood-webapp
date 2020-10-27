package com.criollofood.bootapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReservaController {
    private static final Logger LOGGER = LogManager.getLogger(ReservaController.class);

    @GetMapping(value = "/reservas")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message","El contenido de esta página es sólo para usuarios registrados");
        modelAndView.setViewName("reservas");

        return modelAndView;
    }
}
