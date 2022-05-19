package com.example.api.controle.de.gastos.api.hateoas.assemblers;

import com.example.api.controle.de.gastos.api.dto.receita.ReceitaResp;
import com.example.api.controle.de.gastos.api.resources.ReceitaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReceitaAssembler implements RepresentationModelAssembler<ReceitaResp, EntityModel<ReceitaResp>> {

    @Autowired
    private PagedResourcesAssembler<ReceitaResp> pagedResourcesAssembler;

    @Override
    public EntityModel<ReceitaResp> toModel(ReceitaResp entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ReceitaResource.class).receitaPorId(entity.getId(), "")).withSelfRel(),
                linkTo(methodOn(ReceitaResource.class).removerReceita(entity.getId(), "")).withRel("remover"),
                linkTo(methodOn(ReceitaResource.class).todasAsReceitas(null, "")).withRel("receitas")
        );
    }

    @Override
    public CollectionModel<EntityModel<ReceitaResp>> toCollectionModel(Iterable<? extends ReceitaResp> entities) {

        CollectionModel<EntityModel<ReceitaResp>> receitas =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        receitas.add(linkTo(methodOn(ReceitaResource.class).todasAsReceitas(null, "")).withSelfRel());
        return receitas;
    }

    public PagedModel<EntityModel<ReceitaResp>> toPagedModel(Page<ReceitaResp> entity) {
        return pagedResourcesAssembler.toModel(entity, this);
    }
}
