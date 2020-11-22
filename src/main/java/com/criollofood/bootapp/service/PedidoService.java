package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.DetallePedido;
import com.criollofood.bootapp.domain.Pedido;
import com.criollofood.bootapp.domain.PedidoCocina;
import com.criollofood.bootapp.sql.AgregarAlPedidoSP;
import com.criollofood.bootapp.sql.CambiarEstadoPedidoSp;
import com.criollofood.bootapp.sql.CrearPedidoSP;
import com.criollofood.bootapp.sql.ListarPedidosPendientesSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoService {

    @Autowired
    private CrearPedidoSP crearPedidoSP;
    @Autowired
    private AgregarAlPedidoSP agregarAlPedidoSP;
    @Autowired
    private ListarPedidosPendientesSP listarPedidosPendientesSP;
    @Autowired
    private CambiarEstadoPedidoSp cambiarEstadoPedidoSp;

    public Pedido ingresarPedido(DetallePedido detallePedido) {
        BigDecimal atencionId = detallePedido.getAtencionId();
        BigDecimal pedidoId = crearPedidoSP.execute(atencionId);
        if (Objects.isNull(pedidoId)) {
            return null;
        }
        detallePedido.getItems().forEach((k, v) -> agregarAlPedidoSP.execute(pedidoId, k, v.getCantidad()));

        Pedido pedido = new Pedido(pedidoId);
        pedido.setAtencionId(atencionId);

        return pedido;
    }

    public List<PedidoCocina> listarPedidosPendientes() {
        return listarPedidosPendientesSP.execute();
    }

    public void cambiarEstadoPedido(BigDecimal pedidoId, String estado) {
        cambiarEstadoPedidoSp.execute(pedidoId, estado);
    }
}
