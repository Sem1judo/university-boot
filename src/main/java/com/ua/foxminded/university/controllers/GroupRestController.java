package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.controllers.modelAssembler.FacultyModelAssembler;
import com.ua.foxminded.university.controllers.modelAssembler.GroupModelAssembler;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.services.FacultyServices;
import com.ua.foxminded.university.services.GroupServices;
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
public class GroupRestController {
    private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);


    @Autowired
    @Qualifier("groupModelAssembler")
    private GroupModelAssembler assembler;

    @Autowired
    @Qualifier("groupServices")
    private GroupServices groupServices;


    @GetMapping("/restGroups")
    public CollectionModel<EntityModel<Group>> all() {

        List<EntityModel<Group>> groups = groupServices.getAllLight().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(groups,
                linkTo(methodOn(GroupRestController.class).all()).withSelfRel());
    }

    @PostMapping("/restGroups")
    ResponseEntity<?> newGroup(@RequestBody Group group, @RequestBody Long facultyId) {

        EntityModel<Group> entityModel = assembler.toModel(groupServices.save(group, facultyId));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/restGroups/{groupId}")
    public EntityModel<Group> one(@PathVariable Long groupId) {

        Group group = groupServices.getByIdLight(groupId);

        return assembler.toModel(group);
    }

    @PutMapping("/restGroups/{groupId}")
    ResponseEntity<?> replaceGroup(@RequestBody Group group, @PathVariable Long groupId, @RequestBody Long facultyId) {

        Group updatedGroup = groupServices.findById(groupId). //
                map(groupInternal -> {
            groupInternal.setName(group.getName());
            return groupServices.save(groupInternal, facultyId);
        })
                .orElseGet(() -> {
                    group.setGroupId(groupId);
                    return groupServices.save(group, group.getFaculty().getFacultyId());
                });

        EntityModel<Group> entityModel = assembler.toModel(updatedGroup);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/restGroups/{groupId}")
    ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {

        groupServices.deleteById(groupId);

        return ResponseEntity.noContent().build();
    }

}

