package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Atencion;
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
public class CrearAtencionSP extends StoredProcedure {

    public CrearAtencionSP(@Autowired DataSource dataSource) {
        super(dataSource, "CREAR_ATENCION");

        declareParameter(new SqlParameter("i_cliente_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_mesa_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_atencion_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(Atencion atencion) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_cliente_id", atencion.getClienteId());
        parametersMap.put("i_mesa_id", atencion.getNumeroMesa());

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }

}
