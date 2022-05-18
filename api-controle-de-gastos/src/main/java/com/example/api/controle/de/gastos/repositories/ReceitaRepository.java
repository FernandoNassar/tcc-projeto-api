package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Page<Receita> findByDescricaoContaining(String descricao, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE YEAR(data) = ?1 AND MONTH(data) = ?2")
    Page<Receita> findByYearAndMonth(Integer year, Integer month, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE YEAR(data) = ?1")
    Page<Receita> findByYear(Integer year, Pageable pageable);
}
