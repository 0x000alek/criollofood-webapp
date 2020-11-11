package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Reservacion;
import com.criollofood.bootapp.sql.CrearReservacionSP;
import com.criollofood.bootapp.sql.CancelarReservacionSP;
import com.criollofood.bootapp.sql.ListarReservacionesByIdCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReservacionService {

    @Autowired
    private CrearReservacionSP crearReservacionSP;
    @Autowired
    private CancelarReservacionSP cancelarReservacionSP;
    @Autowired
    private ListarReservacionesByIdCliente listarReservacionesByIdCliente;

    public boolean createReservacion(Reservacion reservacion, BigDecimal idCliente) {
        return crearReservacionSP.execute(reservacion, idCliente);
    }

    public boolean deleteReservacion(BigDecimal idReservacion) {
        return cancelarReservacionSP.execute(idReservacion);
    }

    public List<Reservacion> findByIdCliente(BigDecimal idCliente) {
        return listarReservacionesByIdCliente.execute(idCliente);
    }
}
