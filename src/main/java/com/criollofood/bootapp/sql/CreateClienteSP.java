package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Cliente;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CreateClienteSP extends StoredProcedure {

    public CreateClienteSP(@Autowired DataSource dataSource) {
        super(dataSource, "CREATE_CLIENTE");

        declareParameter(new SqlParameter("i_nombre", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_telefono", OracleTypes.INTEGER));
        declareParameter(new SqlParameter("i_correo", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(Cliente cliente) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_nombre", cliente.getNombre());
        parametersMap.put("i_telefono", cliente.getTelefono().isEmpty() ? null : Integer.parseInt(cliente.getTelefono()));
        parametersMap.put("i_correo", cliente.getCorreo());

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }

}
