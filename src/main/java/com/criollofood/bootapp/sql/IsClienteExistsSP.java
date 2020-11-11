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
public class IsClienteExistsSP extends StoredProcedure {

    public IsClienteExistsSP(@Autowired DataSource dataSource) {
        super(dataSource, "IS_CLIENTE_EXISTS");

        declareParameter(new SqlParameter("i_correo", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(String correoCliente) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_correo", correoCliente));
        BigDecimal userExists = (BigDecimal) resultMap.get("o_sql_code");

        return userExists.compareTo(BigDecimal.ONE) == 0;
    }

}
