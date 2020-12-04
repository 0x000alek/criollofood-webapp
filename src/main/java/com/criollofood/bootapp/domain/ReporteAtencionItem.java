package com.criollofood.bootapp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteAtencionItem {
    private Integer pedidosRealizados;
    private Float promedioTiempo;
    private Float promedioTiempoPreparacion;
    private Float promedioTiempoEntrega;
    private String recetaMasVendida;
}
