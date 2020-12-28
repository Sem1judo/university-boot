package com.ua.foxminded.university.controllers.modelAssembler;

import com.ua.foxminded.university.controllers.GroupRestController;
import com.ua.foxminded.university.model.Group;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupModelAssembler implements RepresentationModelAssembler<Group, EntityModel<Group>> {

    @Override
    public EntityModel<Group> toModel(Group group) {

        return EntityModel.of(group, //
                linkTo(methodOn(GroupRestController.class).one(group.getGroupId())).withSelfRel(),
                linkTo(methodOn(GroupRestController.class).allWithHref()).withRel("restGroupsWithHref"));
    }
}