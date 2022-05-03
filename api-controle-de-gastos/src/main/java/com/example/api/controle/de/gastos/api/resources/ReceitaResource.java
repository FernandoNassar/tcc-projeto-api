package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.receita.ReceitaReq;
import com.example.api.controle.de.gastos.api.dto.receita.ReceitaResp;
import com.example.api.controle.de.gastos.api.services.ReceitaService;
import com.example.api.controle.de.gastos.entities.Receita;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaResource {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReceitaResp>> todasAsReceitas() {
        var receitas = receitaService.findAll();
        var responseBody = receitas.stream().map(r -> modelMapper.map(r, ReceitaResp.class)).toList();
        return ResponseEntity.ok(responseBody);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<ReceitaResp> receitaPorId(@PathVariable("id") Long id) {
        var receita = receitaService.findById(id);
        if (receita == null) return ResponseEntity.notFound().build();
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return ResponseEntity.ok(responseBody);
    }


    @PostMapping
    public ResponseEntity<ReceitaResp> criarReceita(@RequestBody ReceitaReq requestBody, HttpServletRequest req) throws URISyntaxException {
        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.save(receita);
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        var location = new URI(req.getRequestURL().toString());
        return ResponseEntity.created(location).body(responseBody);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReceitaResp> atualizarReceita(@PathVariable("id") Long id, @RequestBody ReceitaReq requestBody) {
        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.update(id, receita);
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return ResponseEntity.ok().body(responseBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerReceita(@PathVariable("id") Long id) {
        receitaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
