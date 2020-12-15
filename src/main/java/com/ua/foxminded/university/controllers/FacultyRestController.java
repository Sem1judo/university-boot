package com.ua.foxminded.university.controllers;


import com.ua.foxminded.university.controllers.modelAssembler.FacultyModelAssembler;
import com.ua.foxminded.university.dao.impl.FacultyRepositoryImpl;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.FacultyServices;
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
public class FacultyRestController {

    private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);


    @Autowired
    @Qualifier("facultyModelAssembler")
    private FacultyModelAssembler assembler;

    @Autowired
    @Qualifier("facultyServices")
    private FacultyServices facultyServices;


    @GetMapping("/restFaculties")
    public CollectionModel<EntityModel<Faculty>> all() {

        List<EntityModel<Faculty>> faculties = facultyServices.getAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(faculties,
                linkTo(methodOn(FacultyRestController.class).all()).withSelfRel());
    }

    @PostMapping("/restFaculties")
    ResponseEntity<?> newFaculty(@RequestBody Faculty faculty) {

        EntityModel<Faculty> entityModel = assembler.toModel(facultyServices.save(faculty));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/restFaculties/{facultyId}")
    public EntityModel<Faculty> one(@PathVariable Long facultyId) {

        Faculty faculty = facultyServices.getById(facultyId);

        return assembler.toModel(faculty);
    }

    @PutMapping("/restFaculties/{facultyId}")
    ResponseEntity<?> replaceFaculty(@RequestBody Faculty faculty, @PathVariable Long facultyId) {

        Faculty updatedFaculty = facultyServices.findById(facultyId) //
                .map(facultyInternal -> {
                    facultyInternal.setName(faculty.getName());
                    return facultyServices.save(facultyInternal);
                })
                .orElseGet(() -> {
                    faculty.setFacultyId(facultyId);
                    return facultyServices.save(faculty);
                });

        EntityModel<Faculty> entityModel = assembler.toModel(updatedFaculty);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/restFaculties/{facultyId}")
    ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {

        facultyServices.deleteById(facultyId);

        return ResponseEntity.noContent().build();
    }

}
