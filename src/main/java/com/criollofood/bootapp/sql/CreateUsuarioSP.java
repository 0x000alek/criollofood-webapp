package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Usuario;
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
public class CreateUsuarioSP extends StoredProcedure {

    public CreateUsuarioSP(@Autowired DataSource dataSource) {
        super(dataSource, "CREATE_USUARIO");

        declareParameter(new SqlParameter("i_username", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_password", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_first_name", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_last_name", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_email", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_is_superuser", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_is_staff", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_grupo_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(Usuario usuario) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_username", usuario.getUsername());
        parametersMap.put("i_password", usuario.getPassword());
        parametersMap.put("i_first_name", usuario.getFirstName());
        parametersMap.put("i_last_name", usuario.getLastName());
        parametersMap.put("i_email", usuario.getEmail());
        parametersMap.put("i_is_superuser", 1);
        parametersMap.put("i_is_staff", 1);
        parametersMap.put("i_grupo_id", 2);

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }
}
