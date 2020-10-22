package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Permiso implements Serializable {
    private static final long serialVersionUID = -5780678208190246480L;

    private BigDecimal id;
    private String name;

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

    @Override
    public String toString() {
        return "Permiso{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
