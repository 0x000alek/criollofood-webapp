package com.criollofood.bootapp.sql;

import com.criollofood.bootapp.domain.Cliente;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Component
public class FindClienteByCorreo extends StoredProcedure {

    public FindClienteByCorreo(@Autowired DataSource dataSource) {
        super(dataSource, "FIND_CLIENTE_BY_CORREO");

        declareParameter(new SqlParameter("i_correo", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_nombre", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_telefono", OracleTypes.INTEGER));
        compile();
    }

    public Cliente execute(String corrreoCliente) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_correo", corrreoCliente));

        Cliente mappedCliente = new Cliente();

        mappedCliente.setId((BigDecimal) resultMap.get("o_id"));
        mappedCliente.setNombre((String) resultMap.get("o_nombre"));
        mappedCliente.setTelefono((String) resultMap.get("o_telefono"));
        mappedCliente.setCorreo(corrreoCliente);

        return mappedCliente;
    }

}
