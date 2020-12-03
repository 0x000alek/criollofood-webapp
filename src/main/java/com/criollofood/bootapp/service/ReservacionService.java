package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Reservacion;
import com.criollofood.bootapp.sql.ListarReservacionesSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservacionService {
    private final ListarReservacionesSP listarReservacionesSP;

    public ReservacionService(@Autowired ListarReservacionesSP listarReservacionesSP) {
        this.listarReservacionesSP = listarReservacionesSP;
    }

    public List<Reservacion> findAll() {
        return listarReservacionesSP.execute();
    }
}
