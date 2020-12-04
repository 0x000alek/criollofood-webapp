package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.PedidoCocina;
import com.criollofood.bootapp.domain.ReporteCajaItem;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReporteCajaSP extends StoredProcedure {

    public ReporteCajaSP(@Autowired DataSource dataSource) {
        super(dataSource, "REPORTE_CAJA");

        declareParameter(new SqlParameter("i_fecha_desde", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_fecha_hasta", OracleTypes.VARCHAR));
        declareParameter(
                new SqlOutParameter("o_reporte_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(ReporteCajaItem.class)));
        compile();
    }

    public List<ReporteCajaItem> execute(String fechaDesde, String fechaHasta) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());
        parametersMap.put("i_fecha_desde", fechaDesde);
        parametersMap.put("i_fecha_hasta", fechaHasta);

        Map<String, Object> resultMap = super.execute(parametersMap);
        return (List<ReporteCajaItem>) resultMap.get("o_reporte_cursor");
    }
}
