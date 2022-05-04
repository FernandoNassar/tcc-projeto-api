package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.despesa.DespesaReq;
import com.example.api.controle.de.gastos.api.dto.despesa.DespesaResp;
import com.example.api.controle.de.gastos.api.services.DespesaService;
import com.example.api.controle.de.gastos.entities.Categoria;
import com.example.api.controle.de.gastos.entities.Despesa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaResource {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ModelMapper modelMapper;



    @GetMapping
    public ResponseEntity<List<DespesaResp>> todasAsReceitas() {
        var despesas = despesaService.findAll();
        var responseBody = despesas.stream().map(d -> modelMapper.map(d, DespesaResp.class)).toList();
        return ResponseEntity.ok(responseBody);
    }



    @GetMapping("/{id}")
    public ResponseEntity<DespesaResp> receitaPorId(@PathVariable("id") Long id) {
        var despesa = despesaService.findById(id);
        if (despesa == null) return ResponseEntity.notFound().build();
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.ok(responseBody);
    }



    @PostMapping
    public ResponseEntity<DespesaResp> criarDespesa(@RequestBody @Valid DespesaReq requestBody, HttpServletRequest req) throws URISyntaxException {
        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.save(despesa);
        var location = new URI(req.getRequestURL().toString());
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.created(location).body(responseBody);
    }



    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DespesaResp> atualizarDespesa(@PathVariable("id") Long id, @RequestBody @Valid DespesaReq requestBody) {
        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.update(id, despesa);
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.ok().body(responseBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDespesa(@PathVariable("id") Long id) {
        despesaService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
