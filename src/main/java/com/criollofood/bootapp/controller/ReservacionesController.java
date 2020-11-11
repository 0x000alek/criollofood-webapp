package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.*;
import com.criollofood.bootapp.service.AtencionService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
public class ReservacionesController {
    private static final Logger LOGGER = LogManager.getLogger(ReservacionesController.class);

    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final ClienteService clienteService;
    private final ReservacionService reservacionService;

    public ReservacionesController(@Autowired AuthenticationFacade authenticationFacade,
                                   @Autowired AtencionService atencionService,
                                   @Autowired ClienteService clienteService,
                                   @Autowired ReservacionService reservacionService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
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
                                 BindingResult bindingResult,
                                 HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("reservar");
        } else {
            String correoCliente = cliente.getCorreo();
            authenticationFacade.authWithoutPassword(correoCliente);
            cliente = clienteService.isClienteExists(correoCliente) ?
                    clienteService.findClienteByCorreo(correoCliente) : new Cliente();
            cliente.setCorreo(correoCliente);
            session.setAttribute("cliente", cliente);
            session.setAttribute("atencion", atencionService.obtenerAtencionByIdCliente(cliente.getId()));
            session.setAttribute("pedid", null);
            //session.setAttribute("atencion", new Atencion());
            session.setAttribute("detallePedido", new DetallePedido());

            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }

    @GetMapping(value = "/reservaciones")
    public ModelAndView reservaciones(HttpSession session) {
        if (!authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/reservar");
        }
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = (Cliente) session.getAttribute("cliente");

        LOGGER.info("cliente: " + cliente.toString());

        modelAndView.addObject("reservacion", new Reservacion(cliente.getNombre(), cliente.getTelefono()));
        modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
        modelAndView.setViewName("reservaciones");

        return modelAndView;
    }

    @PostMapping(value = "/reservaciones")
    public ModelAndView crearReservacion(@Valid @ModelAttribute("reservacion") Reservacion reservacion,
                                         BindingResult bindingResult,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
            modelAndView.setViewName("reservaciones");
        } else {
            if (cliente.getId().compareTo(BigDecimal.ZERO) == 0) {
                cliente = addCliente(reservacion, cliente.getCorreo());
            }
            LOGGER.info("cliente: " + cliente.toString());
            session.setAttribute("cliente", cliente);
            reservacionService.createReservacion(reservacion, cliente.getId());
            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }

    @GetMapping(value = "/reservaciones/cancelar/{id}")
    public ModelAndView cancelarReservacion(@PathVariable BigDecimal id) {
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
