package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Cliente;
import com.criollofood.bootapp.sql.CreateClienteSP;
import com.criollofood.bootapp.sql.FindClienteByCorreo;
import com.criollofood.bootapp.sql.ValidateClienteExistsSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private CreateClienteSP createClienteSP;
    @Autowired
    private FindClienteByCorreo findClienteByCorreo;
    @Autowired
    private ValidateClienteExistsSP validateClienteExistsSP;

    public Cliente createCliente(Cliente cliente) {
        createClienteSP.execute(cliente);
        return findClienteByCorreo(cliente.getCorreo());
    }

    public Cliente findClienteByCorreo(String correoCliente) {
        if (isClienteExists(correoCliente)) {
            return findClienteByCorreo.execute(correoCliente);
        }
        return null;
    }

    public boolean isClienteExists(String correoCliente) {
        return validateClienteExistsSP.execute(correoCliente);
    }
}
