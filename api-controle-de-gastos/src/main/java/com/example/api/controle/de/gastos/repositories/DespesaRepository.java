package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDescricaoContaining(String descricao, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM despesas WHERE YEAR(data) = ?1 AND MONTH(data) = ?2")
    Page<Despesa> findByYearAndMonth(Integer year, Integer month, Pageable pageable);

    @Query(value = "SELECT d FROM Despesa d WHERE d.categoria LIKE :categoria AND MONTH(d.data) = :month AND YEAR(d.data) = :year")
    List<Despesa> findByCategoriaYearAndMonth(Categoria categoria, Integer year, Integer month);

}
