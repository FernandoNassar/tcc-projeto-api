package com.example.api.controle.de.gastos.api.dto.resumo;

import com.example.api.controle.de.gastos.entities.Categoria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResumoMes {

    private BigDecimal valorTotalDeReceita;
    private BigDecimal valorTotalDeDespesa;
    private Map<String, BigDecimal> totalPorCategoria = new LinkedHashMap<>();


    public ResumoMes(BigDecimal valorTotalDeReceita, BigDecimal valorTotalDeDespesa, Map<String, BigDecimal> totalPorCategoria) {
        this.valorTotalDeReceita = valorTotalDeReceita;
        this.valorTotalDeDespesa = valorTotalDeDespesa;
        this.totalPorCategoria = totalPorCategoria;
    }

    public BigDecimal getValorTotalDeReceita() {
        return valorTotalDeReceita;
    }

    public void setValorTotalDeReceita(BigDecimal valorTotalDeReceita) {
        this.valorTotalDeReceita = valorTotalDeReceita;
    }

    public BigDecimal getValorTotalDeDespesa() {
        return valorTotalDeDespesa;
    }

    public void setValorTotalDeDespesa(BigDecimal valorTotalDeDespesa) {
        this.valorTotalDeDespesa = valorTotalDeDespesa;
    }

    public BigDecimal getSaldo() {
        return valorTotalDeReceita.subtract(valorTotalDeDespesa);
    }

    public Map<String, BigDecimal> getTotalPorCategoria() {
        return totalPorCategoria;
    }
}
