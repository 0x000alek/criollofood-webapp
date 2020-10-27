package com.criollofood.bootapp.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 3731733605737805482L;

    private BigDecimal id;
    @NotEmpty(message = "*Porfavor ingresa un nombre de usuario")
    private String username;
    @NotEmpty(message = "*Porfavor ingresa una contraseña")
    private String password;
    @NotEmpty(message = "*Porfavor ingresa tu nombre")
    private String firstName;
    @NotEmpty(message = "*Porfavor ingresa tu apellido")
    private String lastName;
    @NotEmpty(message = "*Porfavor ingresa tu email")
    @Email(message = "*Porfavor ingresa un email válido")
    private String email;

    private List<Grupo> grupos;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", grupos=" + grupos +
                '}';
    }
}
