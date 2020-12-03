package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.PedidoCocina;
import com.criollofood.bootapp.service.PedidoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PedidoController {
    private static final Logger LOGGER = LogManager.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    public PedidoController(@Autowired PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RequestMapping(value = "/pedidos-pendientes", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<PedidoCocina> pedidosPendientes() {
        List<PedidoCocina> pedidosPendientes = pedidoService.listarPedidosPendientes();
        return pedidosPendientes;
    }
}
