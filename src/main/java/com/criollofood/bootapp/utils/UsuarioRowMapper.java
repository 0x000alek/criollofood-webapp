package com.criollofood.bootapp.utils;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.domain.Usuario;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UsuarioRowMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet resultSet, int i) throws SQLException {
        Usuario mappedUsuario = new Usuario();

        mappedUsuario.setId(resultSet.getBigDecimal("o_user_id"));
        mappedUsuario.setUsername(resultSet.getString("o_username"));
        mappedUsuario.setPassword(resultSet.getString("o_password"));
        mappedUsuario.setFirstName(resultSet.getString("o_first_name"));
        mappedUsuario.setLastName(resultSet.getString("o_last_name"));
        mappedUsuario.setEmail(resultSet.getString("o_email"));

        Grupo mappedGrupo = new Grupo();

        mappedGrupo.setId(resultSet.getBigDecimal("o_role_id"));
        mappedGrupo.setName(resultSet.getString("o_role_name"));

        mappedUsuario.setRol(mappedGrupo);

        return mappedUsuario;
    }

}
