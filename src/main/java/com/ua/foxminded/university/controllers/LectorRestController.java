package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.controllers.modelAssembler.LectorModelAssembler;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.services.LectorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LectorRestController {
    private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);


    @Autowired
    @Qualifier("lectorModelAssembler")
    private LectorModelAssembler assembler;

    @Autowired
    @Qualifier("lectorServices")
    private LectorServices lectorServices;


    @GetMapping("/restLectors")
    public CollectionModel<EntityModel<Lector>> all() {

        List<EntityModel<Lector>> lectors = lectorServices.getAllLight().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lectors,
                linkTo(methodOn(LectorRestController.class).all()).withSelfRel());
    }

    @PostMapping("/restLectors")
    ResponseEntity<?> newLector(@RequestBody Lector group, @RequestParam Long facultyId) {

        EntityModel<Lector> entityModel = assembler.toModel(lectorServices.save(group, facultyId));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/restLectors/{lectorId}")
    public EntityModel<Lector> one(@PathVariable Long lectorId) {

        Lector group = lectorServices.getByIdLight(lectorId);

        return assembler.toModel(group);
    }

    @PutMapping("/restLectors/{lectorId}")
    ResponseEntity<?> replaceLector(@RequestBody Lector lector, @PathVariable Long lectorId, @RequestParam Long facultyId) {

        Lector updatedLector = lectorServices.findById(lectorId). //
                map(groupInternal -> {
            groupInternal.setFirstName(lector.getFirstName());
            groupInternal.setLastName(lector.getLastName());
            return lectorServices.save(groupInternal, facultyId);
        })
                .orElseGet(() -> {
                    lector.setLectorId(lectorId);
                    return lectorServices.save(lector, lector.getFaculty().getFacultyId());
                });

        EntityModel<Lector> entityModel = assembler.toModel(updatedLector);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/restLectors/{lectorId}")
    ResponseEntity<?> deleteLector(@PathVariable Long lectorId) {

        lectorServices.deleteById(lectorId);

        return ResponseEntity.noContent().build();
    }

}

