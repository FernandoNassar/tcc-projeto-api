package com.example.api.controle.de.gastos.api.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CadastroReq {
    @NotNull @NotEmpty
    private String username;
    @Email @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
