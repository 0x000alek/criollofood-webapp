package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Receta;
import com.criollofood.bootapp.sql.ListarRecetasDisponibles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    @Autowired
    private ListarRecetasDisponibles listarRecetasDisponibles;

    public List<Receta> findAllDisponibles() {
        return  listarRecetasDisponibles.execute();
    }
}
