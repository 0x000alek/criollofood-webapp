package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.sql.FindGruposByIdUsuarioSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private FindGruposByIdUsuarioSP findGruposByIdUsuarioSP;

    public List<Grupo> findByIdUsuario(BigDecimal idUsuario) {
        List<Grupo> grupos = new ArrayList<>(Collections.emptyList());
        for (Grupo i : findGruposByIdUsuarioSP.execute(idUsuario)) {
            i.setPermisos(permisoService.findByIdGrupo(i.getId()));
            grupos.add(i);
        }
        return grupos;
    }

}
