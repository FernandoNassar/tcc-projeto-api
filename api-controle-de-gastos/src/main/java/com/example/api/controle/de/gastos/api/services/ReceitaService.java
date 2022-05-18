package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Receita;
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


    public Receita findById(Long id) {
        var opReceita = receitaRepository.findById(id);
        return opReceita.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }


    public Page<Receita> findAll(Pageable pageable) {
        return receitaRepository.findAll(pageable);
    }


    public void deleteById(Long id) {
        try {
            receitaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(getErrorMessage(id));
        }
    }


    public Receita update(Long id, Receita receitaAtualizada) {
        var receita = findById(id);
        return atualizarCampos(receita, receitaAtualizada);
    }

    public Page<Receita> findByDescricaoContaining(String descricao, Pageable pageable) {
        return receitaRepository.findByDescricaoContaining(descricao, pageable);
    }

    public Page<Receita> findByYearAndMonth(Integer year, Integer month, Pageable pageable) {
        return receitaRepository.findByYearAndMonth(year, month, pageable);
    }

    public Page<Receita> findByYear(Integer year, Pageable pageable) {
        return receitaRepository.findByYear(year, pageable);
    }

    public Receita save(Receita receita) {
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
