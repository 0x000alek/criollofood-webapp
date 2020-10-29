package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.*;
import com.criollofood.bootapp.service.ClienteService;
import com.criollofood.bootapp.service.ReservacionService;
import com.criollofood.bootapp.utils.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
public class ReservacionesController {
    private static final Logger LOGGER = LogManager.getLogger(ReservacionesController.class);

    private final AuthenticationFacade authenticationFacade;
    private final ClienteService clienteService;
    private final ReservacionService reservacionService;

    public ReservacionesController(@Autowired AuthenticationFacade authenticationFacade,
                                   @Autowired ClienteService clienteService,
                                   @Autowired ReservacionService reservacionService) {
        this.authenticationFacade = authenticationFacade;
        this.clienteService = clienteService;
        this.reservacionService = reservacionService;
    }

    @GetMapping(value = "/reservar")
    public ModelAndView reservar() {
        ModelAndView modelAndView = new ModelAndView();
        if (authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/reservaciones");
        }
        modelAndView.addObject("cliente", new Cliente());
        modelAndView.setViewName("reservar");

        return modelAndView;
    }

    @PostMapping(value = "/reservar")
    public ModelAndView reservar(@Valid @ModelAttribute("cliente") Cliente cliente,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("reservar");
        } else {
            authenticationFacade.authWithoutPassword(cliente.getCorreo());
            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }

    @GetMapping(value = "/reservaciones")
    public ModelAndView reservaciones() {
        ModelAndView modelAndView = new ModelAndView();
        String correoCliente = authenticationFacade.getAuthentication().getPrincipal().toString();
        Cliente cliente = clienteService.findClienteByCorreo(correoCliente);
        if (Objects.isNull(cliente)) {
            cliente = new Cliente();
        }
        modelAndView.addObject("reservacion", new Reservacion(cliente.getNombre(), cliente.getTelefono()));
        modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
        modelAndView.setViewName("reservaciones");

        return modelAndView;
    }

    @PostMapping(value = "/reservaciones")
    public ModelAndView crearReservacion(@Valid @ModelAttribute("reservacion") Reservacion reservacion,
                                 BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        String correoCliente = authenticationFacade.getAuthentication().getPrincipal().toString();
        Cliente cliente = clienteService.findClienteByCorreo(correoCliente);
        if (bindingResult.hasErrors()) {
            if (Objects.isNull(cliente)) {
                cliente = new Cliente();
            }
            modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
            modelAndView.setViewName("reservaciones");
        } else {
            if (Objects.isNull(cliente)) {
                cliente = addCliente(reservacion, correoCliente);
            }
            reservacionService.createReservacion(reservacion, cliente.getId());
            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }

    @GetMapping(value = "/reservaciones/cancelar/{id}")
    public ModelAndView cancelarReservacion(@PathVariable BigDecimal id) {
        LOGGER.info("quieres cancelar la reservacion " + id);
        reservacionService.deleteReservacion(id);
        return new ModelAndView("redirect:/reservaciones");
    }

    private Cliente addCliente(Reservacion reservacion, String correoCliente) {
        Cliente newCliente = new Cliente();

        newCliente.setNombre(reservacion.getNombreCliente());
        newCliente.setTelefono(reservacion.getTelefonoCliente());
        newCliente.setCorreo(correoCliente);

        return clienteService.createCliente(newCliente);
    }

}
