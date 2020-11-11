package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pedido implements Serializable {
    private static final long serialVersionUID = -3570071124220645325L;

    private BigDecimal id;
    private String estado;
    private BigDecimal atencionId;

    public Pedido() {
    }

    public Pedido(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getAtencionId() {
        return atencionId;
    }

    public void setAtencionId(BigDecimal atencionId) {
        this.atencionId = atencionId;
    }

}
