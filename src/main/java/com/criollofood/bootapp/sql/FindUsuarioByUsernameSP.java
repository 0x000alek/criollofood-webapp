package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Grupo;
import com.criollofood.bootapp.domain.Usuario;
import com.criollofood.bootapp.utils.UsuarioRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

@Component
public class FindUsuarioByUsernameSP extends StoredProcedure {
    private static final Logger LOGGER = LogManager.getLogger(FindUsuarioByUsernameSP.class);

    public FindUsuarioByUsernameSP(@Autowired DataSource dataSource,
                                   @Autowired UsuarioRowMapper rowMapper) {
        super(dataSource, "FIND_USUARIO_BY_USERNAME");

        declareParameter(new SqlParameter("i_username", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_user_id", Types.NUMERIC));
        declareParameter(new SqlOutParameter("o_password", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_first_name", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_last_name", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_email", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_grupo_id", Types.NUMERIC));
        declareParameter(new SqlOutParameter("o_grupo_name", Types.VARCHAR));
        compile();
    }

    public Usuario execute(String username) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_username", username));

        Usuario mappedUsuario = new Usuario();

        mappedUsuario.setId((BigDecimal) resultMap.get("o_user_id"));
        mappedUsuario.setUsername(username);
        mappedUsuario.setPassword((String) resultMap.get("o_password"));
        mappedUsuario.setEmail((String) resultMap.get("o_email"));

        Grupo mappedGrupo = new Grupo();

        mappedGrupo.setId((BigDecimal) resultMap.get("o_grupo_id"));
        mappedGrupo.setName((String) resultMap.get("o_grupo_name"));

        mappedUsuario.setRol(mappedGrupo);

        LOGGER.info(mappedUsuario.toString());

        return mappedUsuario;
    }
}
