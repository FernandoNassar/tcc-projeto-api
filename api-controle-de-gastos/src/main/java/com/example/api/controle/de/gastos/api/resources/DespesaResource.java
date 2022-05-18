package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.despesa.DespesaReq;
import com.example.api.controle.de.gastos.api.dto.despesa.DespesaResp;
import com.example.api.controle.de.gastos.api.hateoas.assemblers.DespesaAssembler;
import com.example.api.controle.de.gastos.api.services.DespesaService;
import com.example.api.controle.de.gastos.entities.Despesa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.data.domain.Sort.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    private PagedResourcesAssembler<DespesaResp> despesaResourcesAssembler;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<DespesaResp>> todasAsDespesas(
            @PageableDefault(sort="id", size=10, direction = Direction.ASC) Pageable pageable) {

        var despesas = despesaService.findAll(pageable);
        var responseBody = despesas.map(d -> modelMapper.map(d, DespesaResp.class));
        return despesaResourcesAssembler.toModel(responseBody, despesaAssembler);
    }


    @GetMapping("/{id}")
    public EntityModel<DespesaResp> despesaPorId(@PathVariable("id") Long id) {
        var despesa = despesaService.findById(id);
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return despesaAssembler.toModel(responseBody);
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
