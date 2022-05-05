package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.repositories.DespesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }


    public Despesa findById(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa (id: " + id + ") not found"));
    }


    public List<Despesa> findAll() {
        return despesaRepository.findAll();
    }


    public void deleteById(Long id) {
        try {
            despesaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Despesa (id: " + id + ") not found");
        }
    }


    public Despesa update(Long id, Despesa despesaAtualizada) {
        var despesa = findById(id);
        return atualizarDespesa(despesa, despesaAtualizada);
    }


    public Despesa save(Despesa despesa) {
        return despesaRepository.save(despesa);
    }


    private Despesa atualizarDespesa(Despesa despesa, Despesa despesaAtualizada) {
        despesa.setCategoria(despesaAtualizada.getCategoria());
        despesa.setDescricao(despesaAtualizada.getDescricao());
        despesa.setData(despesaAtualizada.getData());
        despesa.setValor(despesaAtualizada.getValor());
        return despesa;
    }
}
