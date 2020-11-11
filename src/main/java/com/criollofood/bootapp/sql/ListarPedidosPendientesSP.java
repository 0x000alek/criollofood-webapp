package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.ItemPedido;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ListarPedidosPendientesSP extends StoredProcedure {

    public ListarPedidosPendientesSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_PEDIDOS_PENDIENTES");

        declareParameter(
                new SqlOutParameter("o_pedidos_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(ItemPedido.class)));
        compile();
    }

    public List<ItemPedido> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<ItemPedido>) resultMap.get("o_pedidos_cursor");
    }
}
