package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDescricaoContaining(String descricao, Pageable pageable);

}
