package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.PedidoCocina;
import com.criollofood.bootapp.sql.CambiarEstadoPedidoSp;
import com.criollofood.bootapp.sql.ListarPedidosPendientesSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final ListarPedidosPendientesSP listarPedidosPendientesSP;
    private final CambiarEstadoPedidoSp cambiarEstadoPedidoSp;

    public PedidoService(@Autowired ListarPedidosPendientesSP listarPedidosPendientesSP,
                         @Autowired CambiarEstadoPedidoSp cambiarEstadoPedidoSp) {
        this.listarPedidosPendientesSP = listarPedidosPendientesSP;
        this.cambiarEstadoPedidoSp = cambiarEstadoPedidoSp;
    }

    public List<PedidoCocina> listarPedidosPendientes() {
        return listarPedidosPendientesSP.execute();
    }

    public void cambiarEstadoPedido(BigDecimal pedidoId, String estado) {
        cambiarEstadoPedidoSp.execute(pedidoId, estado);
    }
}
