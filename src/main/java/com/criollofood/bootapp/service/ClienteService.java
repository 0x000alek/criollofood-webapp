package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Cliente;
import com.criollofood.bootapp.sql.CrearClienteSP;
import com.criollofood.bootapp.sql.ObtenerClienteByCorreo;
import com.criollofood.bootapp.sql.IsClienteExistsSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private CrearClienteSP crearClienteSP;
    @Autowired
    private ObtenerClienteByCorreo obtenerClienteByCorreo;
    @Autowired
    private IsClienteExistsSP isClienteExistsSP;

    public Cliente createCliente(Cliente cliente) {
        crearClienteSP.execute(cliente);
        return findClienteByCorreo(cliente.getCorreo());
    }

    public Cliente findClienteByCorreo(String correoCliente) {
        if (!isClienteExistsSP.execute(correoCliente)) {
            return null;
        }
        return obtenerClienteByCorreo.execute(correoCliente);
    }

    public boolean isClienteExists(String correoCliente) {
        return isClienteExistsSP.execute(correoCliente);
    }
}
