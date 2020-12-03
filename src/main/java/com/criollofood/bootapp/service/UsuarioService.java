package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.sql.LoginUsuarioSP;
import com.criollofood.bootapp.sql.ObtenerUsuarioByUsernameSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class UsuarioService {
    private final GrupoService grupoService;
    private final ObtenerUsuarioByUsernameSP obtenerUsuarioByUsernameSP;
    private final LoginUsuarioSP loginUsuarioSP;

    public UsuarioService(@Autowired GrupoService grupoService,
                          @Autowired ObtenerUsuarioByUsernameSP obtenerUsuarioByUsernameSP,
                          @Autowired LoginUsuarioSP loginUsuarioSP) {
        this.grupoService = grupoService;
        this.obtenerUsuarioByUsernameSP = obtenerUsuarioByUsernameSP;
        this.loginUsuarioSP = loginUsuarioSP;
    }

    public Usuario findByUsername(String username) {
        Usuario usuario = obtenerUsuarioByUsernameSP.execute(username);
        if (!Objects.isNull(usuario)) {
            usuario.setGrupos(grupoService.findByIdUsuario(usuario.getId()));
        }
        return usuario;
    }

    public boolean updateLastLogin(BigDecimal usuarioId) {
        return loginUsuarioSP.execute(usuarioId);
    }
}
