package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.repositories.DespesaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }


    public Page<Despesa> findAll(Pageable pageable) {
        return despesaRepository.findAll(pageable);
    }


    public void deleteById(Long id) {
        try {
            despesaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(getErrorMessage(id));
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

    public Page<Despesa> findByYearAndMonth(Integer year, Integer month, Pageable pageable) {
        return despesaRepository.findByYearAndMonth(year, month, pageable);
    }

    public List<Despesa> findByCategoriaYearAndMonth(Categoria categoria, Integer year, Integer month) {
        return despesaRepository.findByCategoriaYearAndMonth(categoria, year, month);
    }

    public Page<Despesa> findByDescricaoContaining(String descricao, Pageable pageable) {
        return despesaRepository.findByDescricaoContaining(descricao, pageable);
    }

    private String getErrorMessage(Long id) {
        return "Despesa (id: " + id + ") not found";
    }
}
