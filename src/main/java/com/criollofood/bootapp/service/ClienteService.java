package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.Cliente;
import com.criollofood.bootapp.sql.CrearClienteSP;
import com.criollofood.bootapp.sql.ObtenerClienteByCorreo;
import com.criollofood.bootapp.utils.AESEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClienteService {
    private final AESEncrypter aesEncrypter;

    private final CrearClienteSP crearClienteSP;
    private final ObtenerClienteByCorreo obtenerClienteByCorreo;

    public ClienteService(@Autowired AESEncrypter aesEncrypter,
                          @Autowired CrearClienteSP crearClienteSP,
                          @Autowired ObtenerClienteByCorreo obtenerClienteByCorreo) {
        this.aesEncrypter = aesEncrypter;

        this.crearClienteSP = crearClienteSP;
        this.obtenerClienteByCorreo = obtenerClienteByCorreo;
    }

    public Cliente add(Cliente cliente) {
        cliente.setCorreo(aesEncrypter.encrypt(cliente.getCorreo()));
        return stage(crearClienteSP.execute(cliente));
    }

    public Cliente findByCorreo(String correoCliente) {
        return findByCorreoOrDefault(correoCliente, null);
    }

    public Cliente findByCorreoOrDefault(String correoCliente, Cliente defaultCliente) {
        Cliente cliente = stage(obtenerClienteByCorreo.execute(aesEncrypter.encrypt(correoCliente)));
        return Objects.isNull(cliente) ? defaultCliente : cliente;
    }

    private Cliente stage(Cliente cliente) {
        if (!Objects.isNull(cliente)) {
            cliente.setCorreo(aesEncrypter.decrypt(cliente.getCorreo()));
        }
        return cliente;
    }
}
