package com.example.api.controle.de.gastos.api.services;

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
        return opReceita.orElse(null);
    }

    public List<Receita> findAll() {
        return receitaRepository.findAll();
    }

    public void deleteById(Long id) {
        receitaRepository.deleteById(id);
    }

    public Receita update(Long id, Receita receitaAtualizada) {
        if (receitaRepository.findById(id).isEmpty()) return null;
        var receita = receitaRepository.findById(id).get();
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
