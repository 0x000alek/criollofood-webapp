package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.domain.PedidoCocina;
import com.criollofood.bootapp.domain.Receta;
import com.criollofood.bootapp.service.PedidoService;
import com.criollofood.bootapp.service.RecetaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
public class CocinaController {

    private final PedidoService pedidoService;

    @GetMapping("/cocina")
    public ModelAndView cocina() {
        ModelAndView modelAndView = new ModelAndView();
        List<PedidoCocina> pedidosPendientes = pedidoService.listarPedidosPendientes();
        modelAndView.addObject("pedidos", pedidosPendientes);
        modelAndView.setViewName("cocina");

        return modelAndView;
    }

    @RequestMapping(value="/cocina/pedido/{pedidoId}/cambiar-estado", method= RequestMethod.POST)
    public String cocinaAccionPreparado(@PathVariable BigDecimal pedidoId, @RequestParam String estado) {
        pedidoService.cambiarEstadoPedido(pedidoId, estado);

        return "redirect:/cocina";
    }
}
