package com.example.api.controle.de.gastos.api.dto.receita;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaReq implements Serializable {

    @NotNull @DecimalMin("0.0")
    private BigDecimal valor;

    @NotNull
    private LocalDate data;

    @NotNull @NotEmpty
    private String descricao;

    public ReceitaReq() {}


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
