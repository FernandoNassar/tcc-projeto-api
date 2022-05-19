package com.example.api.controle.de.gastos.api.dto.resumo;

import java.math.BigDecimal;
import java.util.Map;

public class ResumoMes {

    private final BigDecimal valorTotalDeReceita;
    private final BigDecimal valorTotalDeDespesa;
    private final Map<String, BigDecimal> totalPorCategoria;


    public ResumoMes(BigDecimal valorTotalDeReceita, BigDecimal valorTotalDeDespesa, Map<String, BigDecimal> totalPorCategoria) {
        this.valorTotalDeReceita = valorTotalDeReceita;
        this.valorTotalDeDespesa = valorTotalDeDespesa;
        this.totalPorCategoria = totalPorCategoria;
    }

    public BigDecimal getValorTotalDeReceita() {
        return valorTotalDeReceita;
    }

    public BigDecimal getValorTotalDeDespesa() {
        return valorTotalDeDespesa;
    }

    public BigDecimal getSaldo() {
        return valorTotalDeReceita.subtract(valorTotalDeDespesa);
    }

    public Map<String, BigDecimal> getTotalPorCategoria() {
        return totalPorCategoria;
    }
}
