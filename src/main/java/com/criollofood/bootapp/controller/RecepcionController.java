package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.service.AtencionService;
import com.criollofood.bootapp.service.ReservacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class RecepcionController {
    private static final Logger LOGGER = LogManager.getLogger(RecepcionController.class);

    private final ReservacionService reservacionService;
    private final AtencionService atencionService;

    public RecepcionController(@Autowired ReservacionService reservacionService,
                               @Autowired AtencionService atencionService) {
        this.reservacionService = reservacionService;
        this.atencionService = atencionService;
    }

    @GetMapping(value = "/recepcion")
    public ModelAndView recepcion() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("currentDate", new Date());
        modelAndView.addObject("reservaciones", reservacionService.findAll());
        modelAndView.addObject("mesas", atencionService.findAllMesasDisponibles());
        modelAndView.setViewName("recepcion");

        return modelAndView;
    }
}
