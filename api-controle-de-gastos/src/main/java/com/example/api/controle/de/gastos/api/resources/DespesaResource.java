package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.auth.TokenService;
import com.example.api.controle.de.gastos.api.dto.despesa.DespesaReq;
import com.example.api.controle.de.gastos.api.dto.despesa.DespesaResp;
import com.example.api.controle.de.gastos.api.hateoas.assemblers.DespesaAssembler;
import com.example.api.controle.de.gastos.api.services.DespesaService;
import com.example.api.controle.de.gastos.entities.Despesa;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.data.domain.Sort.Direction;

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
    private TokenService tokenService;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<DespesaResp>> todasAsDespesas(
            @PageableDefault(sort = "id", size = 100, direction = Direction.ASC) Pageable pageable,
            @RequestHeader("Authorization") String token) {

        var despesas = despesaService.findAll(tokenService.getUsuario(token), pageable);
        var responseBody = despesas.map(d -> modelMapper.map(d, DespesaResp.class));
        return despesaAssembler.toPagedModel(responseBody);
    }


    @GetMapping("/{id}")
    public EntityModel<DespesaResp> despesaPorId(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String token) {

        var despesa = despesaService.findById(id, tokenService.getUsuario(token));
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return despesaAssembler.toModel(responseBody);
    }


    @PostMapping
    public ResponseEntity<EntityModel<DespesaResp>> criarDespesa(
            @RequestBody @Valid DespesaReq requestBody, HttpServletRequest req,
            @RequestHeader("Authorization") String token) throws URISyntaxException {

        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.save(despesa, tokenService.getUsuario(token));
        var location = new URI(req.getRequestURL().toString() + "/" + despesa.getId());
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return ResponseEntity.created(location).body(despesaAssembler.toModel(responseBody));
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EntityModel<DespesaResp> atualizarDespesa(
            @PathVariable("id") Long id, @RequestBody @Valid DespesaReq requestBody,
            @RequestHeader("Authorization") String token) {

        var despesa = modelMapper.map(requestBody, Despesa.class);
        despesa = despesaService.update(id, despesa, tokenService.getUsuario(token));
        var responseBody = modelMapper.map(despesa, DespesaResp.class);
        return despesaAssembler.toModel(responseBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDespesa(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String token) {

        despesaService.deleteById(id, tokenService.getUsuario(token));
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search/descricao/{descricao}")
    @ResponseStatus(code = HttpStatus.OK)
    public PagedModel<EntityModel<DespesaResp>> despesasPorDescricao(
            @PageableDefault(sort = "descricao", direction = Direction.ASC) Pageable pageable,
            @PathVariable("descricao") String descricao, @RequestHeader("Authorization") String token) {

        var despesas = despesaService.findByDescricaoContaining(descricao, tokenService.getUsuario(token), pageable);
        var responseBody = despesas.map(d -> modelMapper.map(d, DespesaResp.class));
        return despesaAssembler.toPagedModel(responseBody);
    }


    @GetMapping(path = "/search/data/{ano}/{mes}")
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<DespesaResp>> despesasPorMesEAno(
            @PathVariable(name = "ano") Integer ano, @PathVariable(name = "mes") Integer mes,
            @RequestHeader("Authorization") String token) {

        var despesas = despesaService.findByYearAndMonth(ano, mes, tokenService.getUsuario(token));
        var responseBody = despesas.stream().map(d -> modelMapper.map(d, DespesaResp.class)).toList();
        return despesaAssembler.toCollectionModel(responseBody);
    }

}
