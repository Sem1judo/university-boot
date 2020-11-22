package com.ua.foxminded.university.controllers;


import com.ua.foxminded.university.controllers.modelAssembler.TimeSlotModelAssembler;

import com.ua.foxminded.university.model.TimeSlot;
import com.ua.foxminded.university.services.TimeSlotServices;
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
public class TimeSlotRestController {
    private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);


    @Autowired
    @Qualifier("timeSlotModelAssembler")
    private TimeSlotModelAssembler assembler;

    @Autowired
    @Qualifier("timeSlotServices")
    private TimeSlotServices timeSlotServices;


    @GetMapping("/restTimeSlots")
    public CollectionModel<EntityModel<TimeSlot>> all() {

        List<EntityModel<TimeSlot>> timeSlots = timeSlotServices.getAllLight().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(timeSlots,
                linkTo(methodOn(TimeSlotRestController.class).all()).withSelfRel());
    }

    @PostMapping("/restTimeSlots")
    ResponseEntity<?> newTimeSlot(@RequestBody TimeSlot timeSlot, @RequestParam Long lessonId, @RequestParam Long groupId) {

        EntityModel<TimeSlot> timeSlotEntityModel = assembler.toModel(timeSlotServices.save(timeSlot, lessonId, groupId));

        return ResponseEntity
                .created(timeSlotEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(timeSlotEntityModel);
    }

    @GetMapping("/restTimeSlots/{timeSlotId}")
    public EntityModel<TimeSlot> one(@PathVariable Long timeSlotId) {

        TimeSlot timeSlot = timeSlotServices.getByIdLight(timeSlotId);

        return assembler.toModel(timeSlot);
    }

    @PutMapping("/restLectors/{timeSlotId}")
    ResponseEntity<?> replaceLector(@RequestBody TimeSlot timeSlot,@PathVariable Long timeSlotId, @RequestParam Long lessonId, @RequestParam Long groupId) {

        TimeSlot updatedTimeSlot = timeSlotServices.findById(timeSlotId). //
                map(timeSlotInternal -> {
            timeSlotInternal.setStartLesson(timeSlot.getStartLesson());
            timeSlotInternal.setEndLesson(timeSlot.getEndLesson());
            return timeSlotServices.save(timeSlotInternal,lessonId,groupId );
        })
                .orElseGet(() -> {
                    timeSlot.setTimeSlotId(timeSlotId);
                    return timeSlotServices.save(timeSlot, timeSlot.getLesson().getLessonId(),timeSlot.getGroup().getGroupId());
                });

        EntityModel<TimeSlot> entityModel = assembler.toModel(updatedTimeSlot);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/restLectors/{timeSlotId}")
    ResponseEntity<?> deleteTimeSlot(@PathVariable Long timeSlotId) {

        timeSlotServices.deleteById(timeSlotId);

        return ResponseEntity.noContent().build();
    }

}

