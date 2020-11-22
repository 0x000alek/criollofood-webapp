package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Atencion;
import oracle.jdbc.OracleTypes;
import org.hibernate.service.spi.ServiceException;
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
public class CambiarEstadoPedidoSp extends StoredProcedure {

    public CambiarEstadoPedidoSp(@Autowired DataSource dataSource) {
        super(dataSource, "CAMBIAR_ESTADO_PEDIDO");

        declareParameter(new SqlParameter("i_pedido_receta_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_estado", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public void execute(BigDecimal pedidoId, String estado) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("i_pedido_receta_id", pedidoId);
        params.put("i_estado", estado);
        Map<String, Object> resultMap = super.execute(params);
        BigDecimal sqlCode = (BigDecimal) resultMap.get("o_sql_code");
        if (sqlCode.compareTo(BigDecimal.ZERO) == 0) {
            throw new ServiceException("Error al actualizar estado del pedido");
        }
    }
}