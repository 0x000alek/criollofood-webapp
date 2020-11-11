package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable {
    private static final long serialVersionUID = -2616668715759806294L;

    private BigDecimal pedidoId;
    private BigDecimal recetaId;
    private String nombreReceta;
    private int cantidadReceta;

    public BigDecimal getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(BigDecimal pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(BigDecimal recetaId) {
        this.recetaId = recetaId;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public int getCantidadReceta() {
        return cantidadReceta;
    }

    public void setCantidadReceta(int cantidadReceta) {
        this.cantidadReceta = cantidadReceta;
    }
}
