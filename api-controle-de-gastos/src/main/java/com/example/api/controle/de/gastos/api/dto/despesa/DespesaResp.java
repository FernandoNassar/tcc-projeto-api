package com.example.api.controle.de.gastos.api.dto.despesa;

import com.example.api.controle.de.gastos.entities.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;


public class DespesaResp {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
