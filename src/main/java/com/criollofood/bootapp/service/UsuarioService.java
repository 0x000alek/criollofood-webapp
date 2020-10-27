package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.sql.CreateUsuarioSP;
import com.criollofood.bootapp.sql.FindUsuarioByUsernameSP;
import com.criollofood.bootapp.sql.ValidateUsuarioExistsSP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private CreateUsuarioSP createUsuarioSP;
    @Autowired
    private FindUsuarioByUsernameSP findUsuarioByUsernameSP;
    @Autowired
    private ValidateUsuarioExistsSP validateUsuarioExistsSP;

    public Usuario createUsuario(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        createUsuarioSP.execute(usuario);

        return findByUsername(usuario.getUsername());
    }

    public Usuario findByUsername(String username) {
        if (isUsuarioExists(username)) {
            Usuario foundUsuario = findUsuarioByUsernameSP.execute(username);
            foundUsuario.setGrupos(grupoService.findByIdUsuario(foundUsuario.getId()));

            return foundUsuario;
        }
        return null;
    }

    public boolean isUsuarioExists(String username) {
        return validateUsuarioExistsSP.execute(username);
    }

}
