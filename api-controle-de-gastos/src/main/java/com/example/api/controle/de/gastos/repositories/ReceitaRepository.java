package com.example.api.controle.de.gastos.repositories;

import com.example.api.controle.de.gastos.entities.Receita;
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
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Page<Receita> findByDescricaoContainingAndUsuario(String descricao, Usuario usuario, Pageable pageable);

    Page<Receita> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Receita> findByIdAndUsuario(Long id, Usuario usuario);

//    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE YEAR(data) = ?1 AND MONTH(data) = ?2 AND usuario_id = ?3")
//    List<Receita> findByYearAndMonth(Integer year, Integer month, Integer usuarioId);

    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE EXTRACT(YEAR FROM data) = ?1 AND EXTRACT(MONTH FROM data) = ?2 AND usuario_id = ?3")
    List<Receita> findByYearAndMonth(Integer year, Integer month, Integer usuarioId);

//    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE YEAR(data) = ?1")
//    Page<Receita> findByYear(Integer year, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM receitas WHERE EXTRACT(YEAR FROM data) = ?1")
    Page<Receita> findByYear(Integer year, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM receitas WHERE id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
