package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.DetallePedido;
import com.criollofood.bootapp.domain.ItemPedido;
import com.criollofood.bootapp.domain.Pedido;
import com.criollofood.bootapp.sql.AgregarAlPedidoSP;
import com.criollofood.bootapp.sql.CrearPedidoSP;
import com.criollofood.bootapp.sql.ListarPedidosPendientesSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private CrearPedidoSP crearPedidoSP;
    @Autowired
    private AgregarAlPedidoSP agregarAlPedidoSP;
    @Autowired
    private ListarPedidosPendientesSP listarPedidosPendientesSP;

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

    public Map<BigDecimal, ItemPedido> listarPedidosPendientes() {
        return listarPedidosPendientesSP.execute().stream()
                .collect(Collectors.toMap(ItemPedido::getPedidoId, item -> item));
    }
}
