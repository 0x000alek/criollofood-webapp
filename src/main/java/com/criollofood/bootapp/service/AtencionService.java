package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Atencion;
import com.criollofood.bootapp.domain.Mesa;
import com.criollofood.bootapp.sql.CrearAtencionSP;
import com.criollofood.bootapp.sql.ListarMesasSP;
import com.criollofood.bootapp.sql.ObtenerAtencionByIdClienteSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtencionService {
    private final CrearAtencionSP crearAtencionSP;
    private final ObtenerAtencionByIdClienteSP obtenerAtencionByIdClienteSP;
    private final ListarMesasSP listarMesasSP;

    public AtencionService(@Autowired CrearAtencionSP crearAtencionSP,
                           @Autowired ObtenerAtencionByIdClienteSP obtenerAtencionByIdClienteSP,
                           @Autowired ListarMesasSP listarMesasSP) {
        this.crearAtencionSP = crearAtencionSP;
        this.obtenerAtencionByIdClienteSP = obtenerAtencionByIdClienteSP;
        this.listarMesasSP = listarMesasSP;
    }

    public Atencion add(BigDecimal reservacionId, BigDecimal mesaId) {
        return crearAtencionSP.execute(reservacionId, mesaId);
    }

    public Atencion findByIdCliente(BigDecimal clienteId) {
        return obtenerAtencionByIdClienteSP.execute(clienteId);
    }

    public List<Mesa> findAllMesasDisponibles() {
        return listarMesasSP.execute().stream().filter(i -> !i.isEnUso()).collect(Collectors.toList());
    }
}
