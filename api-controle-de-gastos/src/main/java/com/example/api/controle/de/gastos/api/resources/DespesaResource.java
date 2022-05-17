package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.despesa.DespesaReq;
import com.example.api.controle.de.gastos.api.dto.despesa.DespesaResp;
import com.example.api.controle.de.gastos.api.hateoas.assemblers.DespesaAssembler;
import com.example.api.controle.de.gastos.api.services.DespesaService;
import com.example.api.controle.de.gastos.entities.Despesa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/despesas")
public class DespesaResource {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DespesaAssembler despesaAssembler;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<DespesaResp>> todasAsDespesas() {
        var despesas = despesaService.findAll();
        var responseBody = despesas.stream().map(d -> modelMapper.map(d, DespesaResp.class)).toList();
        return despesaAssembler.toCollectionModel(responseBody);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DespesaResp>> despesaPorId(@PathVariable("id") Long id) {
        var despesa = despesaService.findById(id);
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.ok(despesaAssembler.toModel(responseBody));
    }


    @PostMapping
    public ResponseEntity<EntityModel<DespesaResp>> criarDespesa(
            @RequestBody @Valid DespesaReq requestBody, HttpServletRequest req) throws URISyntaxException {

        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.save(despesa);
        var location = new URI(req.getRequestURL().toString());
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.created(location).body(despesaAssembler.toModel(responseBody));
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public EntityModel<DespesaResp> atualizarDespesa(
            @PathVariable("id") Long id, @RequestBody @Valid DespesaReq requestBody) {

        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.update(id, despesa);
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return despesaAssembler.toModel(responseBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDespesa(@PathVariable("id") Long id) {
        despesaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
