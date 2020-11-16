package com.ua.foxminded.university.controllers.modelAssembler;

import com.ua.foxminded.university.controllers.GroupRestController;
import com.ua.foxminded.university.controllers.LectorRestController;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lector;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LectorModelAssembler implements RepresentationModelAssembler<Lector, EntityModel<Lector>> {

    @Override
    public EntityModel<Lector> toModel(Lector lector) {

        return EntityModel.of(lector, //
                linkTo(methodOn(LectorRestController.class).one(lector.getLectorId())).withSelfRel(),
                linkTo(methodOn(LectorRestController.class).all()).withRel("restGroups"));
    }
}