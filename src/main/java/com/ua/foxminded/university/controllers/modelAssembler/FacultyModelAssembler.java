package com.ua.foxminded.university.controllers.modelAssembler;

import com.ua.foxminded.university.model.Faculty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacultyModelAssembler implements RepresentationModelAssembler<Faculty, EntityModel<Faculty>> {

    @Override
    public EntityModel<Faculty> toModel(Faculty faculty) {

        return EntityModel.of(faculty, //
                linkTo(methodOn(FacultyRestController.class).one(faculty.getFacultyId())).withSelfRel(),
                linkTo(methodOn(FacultyRestController.class).all()).withRel("restFaculties"));
    }
}