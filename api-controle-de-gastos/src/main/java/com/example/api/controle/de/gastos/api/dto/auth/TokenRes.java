package com.example.api.controle.de.gastos.api.dto.auth;

public class TokenRes {
    private String tipo;
    private String token;

    public TokenRes(String tipo, String token) {
        this.tipo = tipo;
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
