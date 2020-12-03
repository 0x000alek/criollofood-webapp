package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.service.AtencionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class AtencionController {
    private static final Logger LOGGER = LogManager.getLogger(AtencionController.class);

    private final AtencionService atencionService;

    public AtencionController(@Autowired AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @PostMapping(value = "/atencion/add")
    public ModelAndView crearAtencion(@RequestParam("reservacion-id")BigDecimal reservacionId,
                                      @RequestParam("mesa-id") BigDecimal mesaId) {
        atencionService.add(reservacionId, mesaId);
        return new ModelAndView("redirect:/recepcion");
    }
}
