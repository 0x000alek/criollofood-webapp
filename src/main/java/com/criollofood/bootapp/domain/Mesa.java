package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Mesa implements Serializable {
    private static final long serialVersionUID = -8384836965855473745L;

    private BigDecimal numeroMesa;
    private boolean enUso;
    private Integer asientos;

    public BigDecimal getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(BigDecimal numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "numeroMesa=" + numeroMesa +
                ", enUso=" + enUso +
                ", asientos=" + asientos +
                '}';
    }
}
