package com.example.api.controle.de.gastos.api.hateoas.assemblers;

import com.example.api.controle.de.gastos.api.dto.receita.ReceitaResp;
import com.example.api.controle.de.gastos.api.resources.ReceitaResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReceitaAssembler implements RepresentationModelAssembler<ReceitaResp, EntityModel<ReceitaResp>> {

    @Override
    public EntityModel<ReceitaResp> toModel(ReceitaResp entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ReceitaResource.class).receitaPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(ReceitaResource.class).todasAsReceitas(null)).withRel("receitas")
        );
    }

    @Override
    public CollectionModel<EntityModel<ReceitaResp>> toCollectionModel(Iterable<? extends ReceitaResp> entities) {

        CollectionModel<EntityModel<ReceitaResp>> receitas =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        receitas.add(linkTo(methodOn(ReceitaResource.class).todasAsReceitas(null)).withSelfRel());
        return receitas;
    }
}
