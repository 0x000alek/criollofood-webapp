package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Reservacion;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ListarReservacionesSP extends StoredProcedure {

    public ListarReservacionesSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_RESERVACIONES");

        declareParameter(
                new SqlOutParameter("o_reservaciones_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Reservacion.class))
        );
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Reservacion> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<Reservacion>) resultMap.get("o_reservaciones_cursor");
    }
}
