package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.*;
import com.criollofood.bootapp.service.AtencionService;
import com.criollofood.bootapp.service.ClienteService;
import com.criollofood.bootapp.service.PedidoService;
import com.criollofood.bootapp.utils.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class PedidoController {
    private static final Logger LOGGER = LogManager.getLogger(PedidoController.class);

    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public PedidoController(@Autowired AuthenticationFacade authenticationFacade,
                            @Autowired AtencionService atencionService,
                            @Autowired ClienteService clienteService,
                            @Autowired PedidoService pedidoService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "/pedido")
    public ModelAndView pedido(HttpSession session) {
        if (!authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/reservar");
        }
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        DetallePedido detallePedido = (DetallePedido) session.getAttribute("detallePedido");

        modelAndView.addObject("atencion", (Atencion) session.getAttribute("atencion"));
        modelAndView.addObject("pedido", (Pedido) session.getAttribute("pedido"));
        modelAndView.addObject("itemsPedido", detallePedido.getItems());
        modelAndView.setViewName("pedido");

        return modelAndView;
    }

    @PostMapping(value = "/pedido")
    public ModelAndView confirmar(@RequestParam("atencion-id") BigDecimal atencionId,
                               HttpSession session) {
        DetallePedido detallePedido = (DetallePedido) session.getAttribute("detallePedido");

        detallePedido.setAtencionId(atencionId);
        Pedido pedido = pedidoService.ingresarPedido(detallePedido);

        session.setAttribute("pedido", pedido);
        session.setAttribute("detallePedido", new DetallePedido());

        return new ModelAndView("redirect:/pedido");
    }

    @PostMapping(value = "/pedido/agregar")
    public ModelAndView agregar(@RequestParam("receta-id") BigDecimal recetaId,
                                @RequestParam("receta-nombre") String recetaNombre,
                                @RequestParam("receta-precio") BigDecimal recetaPrecio,
                                @RequestParam("receta-cantidad") Integer recetaCantidad,
                                HttpSession session) {
        DetallePedido detallePedido = (DetallePedido) session.getAttribute("detallePedido");
        Receta receta = new Receta();
        if (detallePedido.getItems().containsKey(recetaId)) {
            recetaCantidad += detallePedido.getItems().get(recetaId).getCantidad();
            receta = detallePedido.getItems().get(recetaId);
        }
        receta.setId(recetaId);
        receta.setNombre(recetaNombre);
        receta.setPrecio(recetaPrecio);
        receta.setCantidad(recetaCantidad);
        detallePedido.getItems().put(recetaId, receta);
        session.setAttribute("detallePedido", detallePedido);

        LOGGER.info("cantidad total: " + detallePedido.getItems().values().stream().mapToInt(Receta::getCantidad).sum());

        return new ModelAndView("redirect:/");
    }

    @PostMapping(value = "/pedido/quitar")
    public ModelAndView quitarDelPedido(@RequestParam("receta-id") BigDecimal recetaId,
                                        @RequestParam("receta-cantidad") Integer recetaCantidad,
                                        HttpSession session) {
        DetallePedido detallePedido = (DetallePedido) session.getAttribute("detallePedido");
        Receta selectedReceta = detallePedido.getItems().get(recetaId);

        int cantidadActual = selectedReceta.getCantidad();
        int nuevaCantidad = cantidadActual - recetaCantidad;

        if (nuevaCantidad == 0) {
            detallePedido.getItems().remove(recetaId);
        } else {
            selectedReceta.setCantidad(nuevaCantidad);
            detallePedido.getItems().put(recetaId, selectedReceta);
        }
        session.setAttribute("detallePedido", detallePedido);

        return new ModelAndView("redirect:/pedido");
    }

    public ModelAndView pedidosPendientes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("");

        return modelAndView;
    }
}
