package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Reservacion;
import com.criollofood.bootapp.sql.CreateReservacionSP;
import com.criollofood.bootapp.sql.DeleteReservacionSP;
import com.criollofood.bootapp.sql.FindReservacionesByIdCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReservacionService {

    @Autowired
    private CreateReservacionSP createReservacionSP;
    @Autowired
    private DeleteReservacionSP deleteReservacionSP;
    @Autowired
    private FindReservacionesByIdCliente findReservacionesByIdCliente;

    public boolean createReservacion(Reservacion reservacion, BigDecimal idCliente) {
        return createReservacionSP.execute(reservacion, idCliente);
    }

    public boolean deleteReservacion(BigDecimal idReservacion) {
        return deleteReservacionSP.execute(idReservacion);
    }

    public List<Reservacion> findByIdCliente(BigDecimal idCliente) {
        return findReservacionesByIdCliente.execute(idCliente);
    }

}
