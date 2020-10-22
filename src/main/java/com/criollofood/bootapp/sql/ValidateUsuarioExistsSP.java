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
public class ValidateUsuarioExistsSP extends StoredProcedure {

    public ValidateUsuarioExistsSP(@Autowired DataSource dataSource) {
        super(dataSource, "VALIDATE_USUARIO_EXISTS");

        declareParameter(new SqlParameter("i_username", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_user_exists", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(String username) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_username", username));
        BigDecimal userExists = (BigDecimal) resultMap.get("o_user_exists");

        return userExists.compareTo(BigDecimal.ZERO) == 0;
    }

}
