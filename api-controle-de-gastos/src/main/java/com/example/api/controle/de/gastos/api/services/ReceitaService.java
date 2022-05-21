package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Receita;
import com.example.api.controle.de.gastos.entities.Usuario;
import com.example.api.controle.de.gastos.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;


    public Receita findById(Long id, Usuario usuario) {
        var opReceita = receitaRepository.findByIdAndUsuario(id, usuario);
        return opReceita.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }


    public Page<Receita> findAll(Usuario usuario, Pageable pageable) {
        return receitaRepository.findByUsuario(usuario, pageable);
    }


    public void deleteById(Long id, Usuario usuario) {
        try {
            findById(id, usuario);
        } catch (Exception e) {
            throw new ResourceNotFoundException(getErrorMessage(id));
        }
        receitaRepository.deleteById(id);
    }


    public Receita update(Long id, Receita receitaAtualizada, Usuario usuario) {
        var receita = findById(id, usuario);
        return atualizarCampos(receita, receitaAtualizada);
    }

    public Page<Receita> findByDescricaoContaining(String descricao, Usuario usuario, Pageable pageable) {
        return receitaRepository.findByDescricaoContainingAndUsuario(descricao, usuario, pageable);
    }

    public List<Receita> findByYearAndMonth(Integer year, Integer month, Usuario usuario) {
        return receitaRepository.findByYearAndMonth(year, month, usuario.getId());
    }


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
