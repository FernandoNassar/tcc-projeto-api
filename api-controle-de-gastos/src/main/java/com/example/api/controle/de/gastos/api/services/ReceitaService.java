package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Receita;
import com.example.api.controle.de.gastos.entities.Usuario;
import com.example.api.controle.de.gastos.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;


    @Cacheable(value = "receitaPorId", key = "{#id, #usuario.toString}")
    public Receita findById(Long id, Usuario usuario) {
        var opReceita = receitaRepository.findByIdAndUsuario(id, usuario);
        return opReceita.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }


    @Cacheable(value = "todasAsReceitas", key = "{#usuario.toString, #pageable.toString}")
    public Page<Receita> findAll(Usuario usuario, Pageable pageable) {
        return receitaRepository.findByUsuario(usuario, pageable);
    }


    @CacheEvict(value = {"receitaPorId", "todasAsReceitas", "buscarReceitas", "receitasPorAnoEMes"}, allEntries = true)
    public void deleteById(Long id, Usuario usuario) {
        try {
            findById(id, usuario);
        } catch (Exception e) {
            throw new ResourceNotFoundException(getErrorMessage(id));
        }
        receitaRepository.deleteById(id);
    }


    @CacheEvict(value = {"receitaPorId", "todasAsReceitas", "receitasPorDescricao", "receitasPorAnoEMes"}, allEntries = true)
    @Transactional
    public Receita update(Long id, Receita receitaAtualizada, Usuario usuario) {
        var receita = findById(id, usuario);
        return atualizarCampos(receita, receitaAtualizada);
    }


    @Cacheable(value = "receitasPorDescricao", key = "{#descricao, #usuario.toString, #pageable.toString}")
    public Page<Receita> findByDescricaoContaining(String descricao, Usuario usuario, Pageable pageable) {
        return receitaRepository.findByDescricaoContainingAndUsuario(descricao, usuario, pageable);
    }


    @Cacheable(value = "receitasPorAnoEMes", key = "{#year, #month, #usuario.toString}")
    public List<Receita> findByYearAndMonth(Integer year, Integer month, Usuario usuario) {
        return receitaRepository.findByYearAndMonth(year, month, usuario.getId());
    }


    @CacheEvict(value = {"receitaPorId", "todasAsReceitas", "receitasPorDescricao", "receitasPorAnoEMes"}, allEntries = true)
    public Receita save(Receita receita, Usuario usuario) {
        receita.setUsuario(usuario);
        return receitaRepository.save(receita);
    }


    private Receita atualizarCampos(Receita receita, Receita receitaAtualizada) {
        receita.setValor(receitaAtualizada.getValor());
        receita.setData(receitaAtualizada.getData());
        receita.setDescricao(receitaAtualizada.getDescricao());
        return receita;
    }

    private String getErrorMessage(Long id) {
        return "Receita (id: " + id + ") not found";
    }
}
