package com.example.api.controle.de.gastos.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "receitas")
public class Receita implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, length = 200)
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Usuario usuario;


    public Receita() { }

    public Receita(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }


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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
