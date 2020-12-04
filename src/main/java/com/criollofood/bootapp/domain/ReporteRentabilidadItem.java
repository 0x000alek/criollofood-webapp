package com.criollofood.bootapp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteRentabilidadItem {
    private String nombre;
    private Integer cantidadVenta;
    private Integer totalVenta;
    private Integer totalPrecioCompra;
    private Float porcentajeGanancia;
    private Integer precioVentaSugerido;
}
