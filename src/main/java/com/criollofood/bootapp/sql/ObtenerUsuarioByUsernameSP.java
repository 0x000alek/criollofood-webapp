package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Usuario;
import oracle.jdbc.OracleTypes;
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
public class ObtenerUsuarioByUsernameSP extends StoredProcedure {

    public ObtenerUsuarioByUsernameSP(@Autowired DataSource dataSource) {
        super(dataSource, "OBTENER_USUARIO_BY_USERNAME");

        declareParameter(new SqlParameter("i_username", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_user_id", Types.NUMERIC));
        declareParameter(new SqlOutParameter("o_password", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_first_name", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_last_name", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_email", Types.VARCHAR));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public Usuario execute(String username) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_username", username));
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        if (resultSqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId((BigDecimal) resultMap.get("o_user_id"));
        usuario.setUsername(username);
        usuario.setPassword((String) resultMap.get("o_password"));
        usuario.setFirstName((String) resultMap.get("o_first_name"));
        usuario.setLastName((String) resultMap.get("o_last_name"));
        usuario.setEmail((String) resultMap.get("o_email"));

        return usuario;
    }
}
