package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class Grupo implements Serializable {
    private static final long serialVersionUID = 6492069354444265000L;

    private BigDecimal id;
    private String name;
    private Set<Permiso> permisoSet;

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

    public Set<Permiso> getPermisoSet() {
        return permisoSet;
    }

    public void setPermisoSet(Set<Permiso> permisoSet) {
        this.permisoSet = permisoSet;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permisoSet=" + permisoSet +
                '}';
    }
}
