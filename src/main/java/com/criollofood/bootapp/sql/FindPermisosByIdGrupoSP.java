package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Permiso;
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
public class FindPermisosByIdGrupoSP extends StoredProcedure {

    public FindPermisosByIdGrupoSP(@Autowired DataSource dataSource) {
        super(dataSource, "FIND_PERMISOS_BY_ID_GRUPO");

        declareParameter(new SqlParameter("i_grupo_id", OracleTypes.NUMBER));
        declareParameter(
                new SqlOutParameter("o_permisos_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Permiso.class)));
        compile();
    }

    public List<Permiso> execute(BigDecimal idGrupo) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_grupo_id", idGrupo));
        return (List<Permiso>) resultMap.get("o_permisos_cursor");
    }

}
