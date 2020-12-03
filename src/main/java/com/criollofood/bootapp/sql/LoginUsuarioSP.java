package com.criollofood.bootapp.sql;

import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Component
public class LoginUsuarioSP extends StoredProcedure {

    public LoginUsuarioSP(@Autowired DataSource dataSource) {
        super(dataSource, "LOGIN_USUARIO");

        declareParameter(new SqlParameter("i_usuario_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(BigDecimal usuarioId) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_usuario_id", usuarioId));
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }
}
