package com.example.api.controle.de.gastos.entities;

public enum Categoria {

    ALIMENTACAO("alimentação"),
    SAUDE("saúde"),
    MORADIA("moradia"),
    TRANSPORTE("transporte"),
    LAZER("lazer"),
    IMPREVISTOS("imprevistos"),
    OUTRAS("outras");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
