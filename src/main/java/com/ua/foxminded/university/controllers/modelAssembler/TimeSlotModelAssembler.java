package com.ua.foxminded.university.controllers.modelAssembler;

import com.ua.foxminded.university.controllers.TimeSlotRestController;
import com.ua.foxminded.university.model.TimeSlot;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TimeSlotModelAssembler implements RepresentationModelAssembler<TimeSlot, EntityModel<TimeSlot>> {

    @Override
    public EntityModel<TimeSlot> toModel(TimeSlot timeSlot) {

        return EntityModel.of(timeSlot, //
                linkTo(methodOn(TimeSlotRestController.class).one(timeSlot.getTimeSlotId())).withSelfRel(),
                linkTo(methodOn(TimeSlotRestController.class).all()).withRel("restFaculties"));
    }
}