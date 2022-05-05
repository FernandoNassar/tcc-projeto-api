package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Receita;
import com.example.api.controle.de.gastos.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;


    public Receita findById(Long id) {
        var opReceita = receitaRepository.findById(id);
        return opReceita.orElseThrow(() -> new ResourceNotFoundException("Receita (id: " + id + ") not found"));
    }


    public List<Receita> findAll() {
        return receitaRepository.findAll();
    }


    public void deleteById(Long id) {
        try {
            receitaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Receita (id: " + id + ") not found");
        }
    }


    public Receita update(Long id, Receita receitaAtualizada) {
        var receita = findById(id);
        return atualizarCampos(receita, receitaAtualizada);
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
}
