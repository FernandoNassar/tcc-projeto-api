package com.example.api.controle.de.gastos.api.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaReq {

    private BigDecimal valor;
    private LocalDate data;
    private String descricao;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
