package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import com.example.api.controle.de.gastos.entities.Usuario;
import com.example.api.controle.de.gastos.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;


    @Cacheable(value = "despesaPorId", key = "{#id, #usuario.id}")
    public Despesa findById(Long id, Usuario usuario) {
        return despesaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }


    @Cacheable(value = "todasAsDespesas", key = "{#usuario.id, #pageable}")
    public Page<Despesa> findAll(Usuario usuario, Pageable pageable) {
        return despesaRepository.findByUsuario(usuario, pageable);
    }


    @CacheEvict(value = {"todasAsDespesas", "despesaPorId", "despesasPorMesEAno", "despesasPorDescricao"}, allEntries = true)
    public void deleteById(Long id, Usuario usuario) {
        try {
            findById(id, usuario);
            despesaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(getErrorMessage(id));
        }
    }

    @CacheEvict(value = {"todasAsDespesas", "despesaPorId", "despesasPorMesEAno", "despesasPorDescricao"}, allEntries = true)
    @Transactional
    public Despesa update(Long id, Despesa despesaAtualizada, Usuario usuario) {
        var despesa = findById(id, usuario);
        return atualizarDespesa(despesa, despesaAtualizada);
    }


    @CacheEvict(value = {"todasAsDespesas", "despesaPorId", "despesasPorMesEAno", "despesasPorDescricao"}, allEntries = true)
    public Despesa save(Despesa despesa, Usuario usuario) {
        despesa.setUsuario(usuario);
        return despesaRepository.save(despesa);
    }


    private Despesa atualizarDespesa(Despesa despesa, Despesa despesaAtualizada) {
        despesa.setCategoria(despesaAtualizada.getCategoria());
        despesa.setDescricao(despesaAtualizada.getDescricao());
        despesa.setData(despesaAtualizada.getData());
        despesa.setValor(despesaAtualizada.getValor());
        return despesa;
    }

    @Cacheable(value = "despesasPorMesEAno", key = "{#year, #month, #usuario.id}")
    public List<Despesa> findByYearAndMonth(Integer year, Integer month, Usuario usuario) {
        return despesaRepository.findByYearAndMonth(year, month, usuario.getId())
                .stream().filter(d -> d.getUsuario().getId().equals(usuario.getId())).toList();
    }

    public List<Despesa> findByCategoriaYearAndMonth(Categoria categoria, Integer year, Integer month, Usuario usuario) {
        return despesaRepository.findByCategoriaYearAndMonth(categoria, year, month).stream().filter(c -> c.getUsuario() == usuario).toList();
    }

    @Cacheable(value = "despesasPorDescricao", key = "{#descricao, #usuario.id, #pageable}")
    public Page<Despesa> findByDescricaoContaining(String descricao, Usuario usuario, Pageable pageable) {
        return despesaRepository.findByDescricaoContainingAndUsuario(descricao, usuario, pageable);
    }

    private String getErrorMessage(Long id) {
        return "Despesa (id: " + id + ") not found";
    }
}
