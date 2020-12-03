package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.sql.ListarGruposByIdUsuarioSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GrupoService {

    private final ListarGruposByIdUsuarioSP listarGruposByIdUsuarioSP;

    public GrupoService(@Autowired ListarGruposByIdUsuarioSP listarGruposByIdUsuarioSP) {
        this.listarGruposByIdUsuarioSP = listarGruposByIdUsuarioSP;
    }

    public List<Grupo> findByIdUsuario(BigDecimal idUsuario) {
        return listarGruposByIdUsuarioSP.execute(idUsuario);
    }
}
