package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Mesa;
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
public class ListarMesasSP extends StoredProcedure {

    public ListarMesasSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_MESAS");

        declareParameter(
                new SqlOutParameter("o_mesas_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Mesa.class))
        );
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Mesa> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<Mesa>) resultMap.get("o_mesas_cursor");
    }
}
