package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Grupo;
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
public class ListarGruposByIdUsuarioSP extends StoredProcedure {

    public ListarGruposByIdUsuarioSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_GRUPOS_BY_ID_USUARIO");

        declareParameter(new SqlParameter("i_usuario_id", OracleTypes.NUMBER));
        declareParameter(
                new SqlOutParameter("o_grupos_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Grupo.class)));
        compile();
    }

    public List<Grupo> execute(BigDecimal idUsuario) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_usuario_id", idUsuario));
        return (List<Grupo>) resultMap.get("o_grupos_cursor");
    }

}
