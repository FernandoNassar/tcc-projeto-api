package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.auth.TokenService;
import com.example.api.controle.de.gastos.api.dto.receita.ReceitaReq;
import com.example.api.controle.de.gastos.api.dto.receita.ReceitaResp;
import com.example.api.controle.de.gastos.api.hateoas.assemblers.ReceitaAssembler;
import com.example.api.controle.de.gastos.api.services.ReceitaService;
import com.example.api.controle.de.gastos.entities.Receita;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static org.springframework.data.domain.Sort.Direction;

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
    private TokenService tokenService;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<ReceitaResp>> todasAsReceitas(
            @PageableDefault(sort = "id", size = 100, direction = Direction.ASC) Pageable pageable,
            @RequestHeader("Authorization") String token) {

        var receitas = receitaService.findAll(tokenService.getUsuario(token), pageable);
        var responseBody = receitas.map(r -> modelMapper.map(r, ReceitaResp.class));
        return receitaAssembler.toPagedModel(responseBody);
    }


    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EntityModel<ReceitaResp> receitaPorId(
            @PathVariable("id") Long id, @RequestHeader("Authorization") String token) {

        var receita = receitaService.findById(id, tokenService.getUsuario(token));
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return receitaAssembler.toModel(responseBody);
    }


    @PostMapping
    public ResponseEntity<EntityModel<ReceitaResp>> criarReceita(
            @RequestBody @Valid ReceitaReq requestBody, @RequestHeader("Authorization") String token,
            HttpServletRequest req) throws URISyntaxException {

        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.save(receita, tokenService.getUsuario(token));
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        var location = new URI(req.getRequestURL().toString());
        return ResponseEntity.created(location).body(receitaAssembler.toModel(responseBody));
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public EntityModel<ReceitaResp> atualizarReceita(
            @PathVariable("id") Long id, @RequestBody @Valid ReceitaReq requestBody,
            @RequestHeader("Authorization") String token) {

        var receita = modelMapper.map(requestBody, Receita.class);
        receita = receitaService.update(id, receita, tokenService.getUsuario(token));
        var responseBody = modelMapper.map(receita, ReceitaResp.class);
        return receitaAssembler.toModel(responseBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerReceita(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        receitaService.deleteById(id, tokenService.getUsuario(token));
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search/descricao/{descricao}")
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<ReceitaResp>> buscarReceitas(
            @PageableDefault(sort = "id", size = 100, direction = Direction.ASC) Pageable pageable,
            @PathVariable("descricao") String descricao, @RequestHeader("Authorization") String token) {

        var receitas = receitaService.findByDescricaoContaining(descricao, tokenService.getUsuario(token), pageable);
        var responseBody = receitas.map(r -> modelMapper.map(r, ReceitaResp.class));
        return receitaAssembler.toPagedModel(responseBody);
    }


    @GetMapping(path = "/search/data/{ano}/{mes}")
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<ReceitaResp>> receitasPorAnoEMes(
            @PathVariable(name = "ano") Integer ano, @PathVariable(name = "mes") Integer mes,
            @RequestHeader("Authorization") String token) {

        var receitas = receitaService.findByYearAndMonth(ano, mes, tokenService.getUsuario(token));
        var responseBody = receitas.stream().map(r -> modelMapper.map(r, ReceitaResp.class)).toList();
        return receitaAssembler.toCollectionModel(responseBody);
    }

}
