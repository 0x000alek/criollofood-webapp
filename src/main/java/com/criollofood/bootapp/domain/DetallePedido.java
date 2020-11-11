package com.criollofood.bootapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetallePedido implements Serializable {
    private static final long serialVersionUID = 3069879901656089387L;

    private BigDecimal atencionId;
    private Map<BigDecimal, Receta> items = new LinkedHashMap<>(Collections.emptyMap());

    public BigDecimal getAtencionId() {
        return atencionId;
    }

    public void setAtencionId(BigDecimal atencionId) {
        this.atencionId = atencionId;
    }

    public Map<BigDecimal, Receta> getItems() {
        return items;
    }

    public void setItems(Map<BigDecimal, Receta> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "atencionId=" + atencionId +
                ", items=" + items +
                '}';
    }

}
