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
import java.util.HashMap;
import java.util.Map;

@Component
public class AgregarAlPedidoSP extends StoredProcedure {

    public AgregarAlPedidoSP(@Autowired DataSource dataSource) {
        super(dataSource, "AGREGAR_AL_PEDIDO");

        declareParameter(new SqlParameter("i_pedido_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_receta_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_cantidad", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(BigDecimal pedidoId, BigDecimal recetaId, Integer cantidad) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_pedido_id", pedidoId);
        parametersMap.put("i_receta_id", recetaId);
        parametersMap.put("i_cantidad", cantidad);

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal sqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return sqlCode.compareTo(BigDecimal.ONE) == 0;
    }

}
