package com.example.api.controle.de.gastos.api.configs;

import com.example.api.controle.de.gastos.api.services.ReceitaService;
import com.example.api.controle.de.gastos.api.services.UsuarioService;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.entities.Receita;
import com.example.api.controle.de.gastos.entities.Usuario;
import com.example.api.controle.de.gastos.repositories.DespesaRepository;
import com.example.api.controle.de.gastos.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InitDatabase {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Bean
    public CommandLineRunner initReceitasTable() {
        return args -> {
            receitaRepository.save(new Receita("Teste Receita", new BigDecimal("150.00"), LocalDate.now()));
            receitaRepository.save(new Receita("Teste Receita1", new BigDecimal("150.00"), LocalDate.now()));
            receitaRepository.save(new Receita("Teste Receita2", new BigDecimal("150.00"), LocalDate.now()));
        };
    }

    @Bean
    public CommandLineRunner initDespesasTable() {
        return args -> {
            despesaRepository.save(new Despesa("Teste Despesa", new BigDecimal("100.0"), LocalDate.now(), Categoria.ALIMENTACAO));
            despesaRepository.save(new Despesa("Teste Despesa 1", new BigDecimal("100.0"), LocalDate.now(), Categoria.LAZER));
            despesaRepository.save(new Despesa("Teste Despesa 2", new BigDecimal("100.0"), LocalDate.now(), Categoria.TRANSPORTE));
        };
    }

    @Bean
    public CommandLineRunner initUsuariosTable() {
        return args -> {
            usuarioService.save(new Usuario("teste1", "teste1@teste1.com", "1234"));
        };
    }

}
