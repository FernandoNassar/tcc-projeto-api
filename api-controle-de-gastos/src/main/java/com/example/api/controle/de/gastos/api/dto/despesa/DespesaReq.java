package com.example.api.controle.de.gastos.api.dto.despesa;

import com.example.api.controle.de.gastos.entities.Categoria;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaReq {

    @NotNull @NotEmpty
    private String descricao;
    @NotNull @DecimalMin(value = "0.0")
    private BigDecimal valor;
    @NotNull
    private LocalDate data;
    @NotNull
    private Categoria categoria;

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
