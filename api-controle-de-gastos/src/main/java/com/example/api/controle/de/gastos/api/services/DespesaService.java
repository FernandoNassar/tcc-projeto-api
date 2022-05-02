package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.repositories.DespesaRepository;

import java.util.List;

public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public Despesa findById(Long id) {
        return despesaRepository.findById(id).orElse(null);
    }

    public List<Despesa> findAll() {
        return despesaRepository.findAll();
    }

    public void delete(Long id) {
        despesaRepository.deleteById(id);
    }

    private Despesa update(Long id, Despesa despesaAtualizada) {
        if(despesaRepository.findById(id).isEmpty()) return null;
        var despesa = despesaRepository.findById(id).get();
        return atualizarDespesa(despesa, despesaAtualizada);
    }

    private Despesa atualizarDespesa(Despesa despesa, Despesa despesaAtualizada) {
        despesa.setCategoria(despesaAtualizada.getCategoria());
        despesa.setDescricao(despesaAtualizada.getDescricao());
        despesa.setData(despesaAtualizada.getData());
        despesa.setValor(despesaAtualizada.getValor());
        return despesa;
    }

}
