package com.example.api.controle.de.gastos.api.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaResp {

    private Long id;
    private BigDecimal valor;
    private LocalDate data;
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
