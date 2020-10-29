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
public class DeleteReservacionSP extends StoredProcedure {

    public DeleteReservacionSP(@Autowired DataSource dataSource) {
        super(dataSource, "DELETE_RESERVACION");

        declareParameter(new SqlParameter("i_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(BigDecimal idReservacion) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_id", idReservacion));
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }

}
