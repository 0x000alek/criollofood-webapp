package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.Atencion;
import com.criollofood.bootapp.domain.Cliente;
import com.criollofood.bootapp.domain.DetallePedido;
import com.criollofood.bootapp.service.AtencionService;
import com.criollofood.bootapp.service.ClienteService;
import com.criollofood.bootapp.service.RecetaService;
import com.criollofood.bootapp.utils.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class HomeController {

    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final ClienteService clienteService;
    private RecetaService recetaService;

    public HomeController(@Autowired AuthenticationFacade authenticationFacade,
                          @Autowired AtencionService atencionService,
                          @Autowired ClienteService clienteService,
                          @Autowired RecetaService recetaService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
        this.clienteService = clienteService;
        this.recetaService = recetaService;
    }

    @GetMapping(value= "/")
    public ModelAndView home(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if (authenticationFacade.isAuthenticated()) {
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            //modelAndView.addObject("atencion", atencionService.obtenerAtencionByIdCliente(cliente.getId()));
            modelAndView.addObject("atencion", session.getAttribute("atencion"));
        }
        modelAndView.addObject("recetas", recetaService.findAllDisponibles());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value= "/index")
    public ModelAndView index(){
        return new ModelAndView("redirect:/");
    }
}
