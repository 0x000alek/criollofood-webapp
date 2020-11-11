package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Atencion;
import com.criollofood.bootapp.sql.ObtenerAtencionByIdClienteSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AtencionService {

    @Autowired
    private ObtenerAtencionByIdClienteSP obtenerAtencionByIdClienteSP;

    public Atencion obtenerAtencionByIdCliente(BigDecimal clienteId) {
        return obtenerAtencionByIdClienteSP.execute(clienteId);
    }

}
