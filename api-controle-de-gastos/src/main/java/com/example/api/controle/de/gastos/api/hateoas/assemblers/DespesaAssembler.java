package com.example.api.controle.de.gastos.api.hateoas.assemblers;

import com.example.api.controle.de.gastos.api.dto.despesa.DespesaResp;
import com.example.api.controle.de.gastos.api.resources.DespesaResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DespesaAssembler implements RepresentationModelAssembler<DespesaResp, EntityModel<DespesaResp>> {

    @Override
    public EntityModel<DespesaResp> toModel(DespesaResp entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(DespesaResource.class).despesaPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(DespesaResource.class).todasAsDespesas(null)).withRel("despesas")
        );
    }

    @Override
    public CollectionModel<EntityModel<DespesaResp>> toCollectionModel(Iterable<? extends DespesaResp> entities) {
        CollectionModel<EntityModel<DespesaResp>> despesas = RepresentationModelAssembler.super.toCollectionModel(entities);
        despesas.add(linkTo(methodOn(DespesaResource.class).todasAsDespesas(null)).withSelfRel());
        return despesas;
    }
}
