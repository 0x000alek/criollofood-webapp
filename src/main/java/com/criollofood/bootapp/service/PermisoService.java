package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Permiso;
import com.criollofood.bootapp.sql.FindPermisosByIdGrupoSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PermisoService {

    @Autowired
    private FindPermisosByIdGrupoSP findPermisosByIdGrupoSP;

    public List<Permiso> findByIdGrupo(BigDecimal idGrupo) {
        return findPermisosByIdGrupoSP.execute(idGrupo);
    }

}
