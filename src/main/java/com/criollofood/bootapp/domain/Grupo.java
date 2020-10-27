package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Grupo implements Serializable {
    private static final long serialVersionUID = 6492069354444265000L;

    private BigDecimal id;
    private String name;
    private List<Permiso> permisos;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permisos=" + permisos +
                '}';
    }
}
