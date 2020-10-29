package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Usuario;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class FindAllUsuariosSP extends StoredProcedure {

    public FindAllUsuariosSP(@Autowired DataSource dataSource) {
        super(dataSource, "FIND_ALL_USUARIOS");

        declareParameter(
                new SqlOutParameter("o_usuarios_cursor", OracleTypes.CURSOR));
        compile();
    }

    public List<Usuario> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return null;
    }
}
