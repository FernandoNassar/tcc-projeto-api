package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.dto.resumo.ResumoMes;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.entities.Receita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumoService {

    @Autowired
    private DespesaService despesaService;
    @Autowired
    private ReceitaService receitaService;


    public ResumoMes getResumoMes(Integer mes, Integer ano) {
        var valorTotalDeReceitas = getValorTotalDeReceitas(receitaService.findByYearAndMonth(ano, mes, null));
        var valorTotalDeDespesas = getValorTotalDeDespesas(despesaService.findByYearAndMonth(ano, mes, null));
        var categorias = Arrays.asList(Categoria.values());
        return new ResumoMes(valorTotalDeReceitas, valorTotalDeDespesas, getTotalPorCategoria(categorias, ano, mes));
    }

    private BigDecimal getValorTotalDeDespesas(Page<Despesa> despesas) {
        return despesas.stream().map(Despesa::getValor).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private BigDecimal getValorTotalDeReceitas(Page<Receita> receitas) {
        return receitas.stream().map(Receita::getValor).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private Map<String, BigDecimal> getTotalPorCategoria(List<Categoria> categorias, Integer year, Integer month) {
        var total = new LinkedHashMap<String, BigDecimal>();
        categorias.forEach(c ->
                total.put(
                        c.getDescricao(),
                        despesaService.findByCategoriaYearAndMonth(c, year, month)
                                .stream().map(Despesa::getValor)
                                .reduce(BigDecimal::add)
                                .orElse(BigDecimal.ZERO)
                )
        );
        return total;
    }
}
