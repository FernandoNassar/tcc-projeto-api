package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.receita.ReceitaReq;
import com.example.api.controle.de.gastos.api.dto.receita.ReceitaResp;
import com.example.api.controle.de.gastos.api.hateoas.assemblers.ReceitaAssembler;
import com.example.api.controle.de.gastos.api.services.ReceitaService;
import com.example.api.controle.de.gastos.entities.Receita;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/receitas")
public class ReceitaResource {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReceitaAssembler receitaAssembler;

    @Autowired
    private PagedResourcesAssembler<ReceitaResp> receitaResourcesAssembler;



    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<ReceitaResp>> todasAsReceitas(
            @PageableDefault(sort="id", size=10, direction = Sort.Direction.ASC) Pageable pageable) {

        var receitas = receitaService.findAll(pageable);
        var responseBody = receitas.map(r -> modelMapper.map(r, ReceitaResp.class));
        return receitaResourcesAssembler.toModel(responseBody, receitaAssembler);
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<ReceitaResp>> receitaPorId(@PathVariable("id") Long id) {
        var receita = receitaService.findById(id);
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return ResponseEntity.ok(receitaAssembler.toModel(responseBody));
    }



    @PostMapping
    public ResponseEntity<EntityModel<ReceitaResp>> criarReceita(
            @RequestBody @Valid ReceitaReq requestBody, HttpServletRequest req) throws URISyntaxException {

        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.save(receita);
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        var location = new URI(req.getRequestURL().toString());
        return ResponseEntity.created(location).body(receitaAssembler.toModel(responseBody));
    }



    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public EntityModel<ReceitaResp> atualizarReceita(@PathVariable("id") Long id, @RequestBody @Valid ReceitaReq requestBody) {
        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.update(id, receita);
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return receitaAssembler.toModel(responseBody);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerReceita(@PathVariable("id") Long id) {
        receitaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
