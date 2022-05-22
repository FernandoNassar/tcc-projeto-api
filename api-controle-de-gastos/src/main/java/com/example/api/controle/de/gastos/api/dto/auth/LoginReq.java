package com.example.api.controle.de.gastos.api.dto.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginReq {
    @NotNull @NotEmpty
    private String username;
    @NotNull @NotEmpty
    private String password;

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
}
