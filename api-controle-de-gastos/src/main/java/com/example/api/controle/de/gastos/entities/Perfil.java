package com.example.api.controle.de.gastos.entities;

import javax.persistence.*;

@Entity
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String titulo;

    public Perfil() {}

    public Perfil(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
