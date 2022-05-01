package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

}
