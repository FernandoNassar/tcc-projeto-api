package com.example.api.controle.de.gastos.api.configs;

import com.example.api.controle.de.gastos.api.services.DespesaService;
import com.example.api.controle.de.gastos.api.services.ReceitaService;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.entities.Receita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InitDatabase {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private DespesaService despesaService;


    @Bean
    public CommandLineRunner initReceitasTable() {
        return args -> {
            receitaService.save(new Receita("Teste Receita", new BigDecimal("150.00"), LocalDate.now()));
            receitaService.save(new Receita("Teste Receita1", new BigDecimal("150.00"), LocalDate.now()));
            receitaService.save(new Receita("Teste Receita2", new BigDecimal("150.00"), LocalDate.now()));
        };
    }

    @Bean
    public CommandLineRunner initDespesasTable() {
        return args -> {
            despesaService.save(new Despesa("Teste Despesa", new BigDecimal("100.0"), LocalDate.now(), Categoria.ALIMENTACAO));
            despesaService.save(new Despesa("Teste Despesa 1", new BigDecimal("100.0"), LocalDate.now(), Categoria.LAZER));
            despesaService.save(new Despesa("Teste Despesa 2", new BigDecimal("100.0"), LocalDate.now(), Categoria.TRANSPORTE));
        };
    }
}
