package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDescricaoContainingAndUsuario(String descricao, Usuario usuario, Pageable pageable);

    Page<Despesa> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Despesa> findByIdAndUsuario(Long id, Usuario usuario);

    @Query(nativeQuery = true, value = "SELECT * FROM despesas WHERE EXTRACT(YEAR FROM data) = ?1 AND EXTRACT(MONTH FROM data) = ?2 AND usuario_id = ?3")
    List<Despesa> findByYearAndMonth(Integer year, Integer month, Integer usuarioId);

    @Query(value = "SELECT d FROM Despesa d WHERE d.categoria LIKE :categoria AND MONTH(d.data) = :month AND YEAR(d.data) = :year")
    List<Despesa> findByCategoriaYearAndMonth(Categoria categoria, Integer year, Integer month);

    @Modifying
    @Query(value = "DELETE FROM despesas WHERE id = ?1", nativeQuery = true)
    void deleteById(Long id);

}
