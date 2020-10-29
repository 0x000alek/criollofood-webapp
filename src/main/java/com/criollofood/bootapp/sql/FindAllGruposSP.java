package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Grupo;
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
public class FindAllGruposSP extends StoredProcedure {

    public FindAllGruposSP(@Autowired DataSource dataSource) {
        super(dataSource, "FIND_ALL_GRUPOS");

        declareParameter(
                new SqlOutParameter("o_grupos_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Grupo.class)));
        compile();
    }

    public List<Grupo> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<Grupo>) resultMap.get("o_grupos_cursor");
    }
}
