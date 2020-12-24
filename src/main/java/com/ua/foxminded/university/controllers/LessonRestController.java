package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.controllers.modelAssembler.LessonModelAssembler;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.services.LessonServices;
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
public class LessonRestController {
    private static final Logger logger = LoggerFactory.getLogger(LessonRestController.class);


    @Autowired
    @Qualifier("lessonModelAssembler")
    private LessonModelAssembler assembler;

    @Autowired
    @Qualifier("lessonServices")
    private LessonServices lessonServices;


    @GetMapping("/restLessons")
    public CollectionModel<EntityModel<Lesson>> all() {

        List<EntityModel<Lesson>> groups = lessonServices.getAllLight().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(groups,
                linkTo(methodOn(LessonRestController.class).all()).withSelfRel());
    }

    @PostMapping("/restLessons")
    ResponseEntity<?> newLesson(@RequestBody Lesson lesson, @RequestParam Long lectorId, @RequestParam Long facultyId) {

        EntityModel<Lesson> entityModel = assembler.toModel(lessonServices.save(lesson, lectorId,facultyId));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/restLessons/{lessonId}")
    public EntityModel<Lesson> one(@PathVariable Long lessonId) {

        Lesson lesson = lessonServices.getByIdLight(lessonId);

        return assembler.toModel(lesson);
    }

    @PutMapping("/restLessons/{lessonId}")
    ResponseEntity<?> replaceLesson(@RequestBody Lesson lesson, @PathVariable Long lessonId,
                                    @RequestParam Long lectorId,
                                    @RequestParam Long facultyId) {

        Lesson updatedLesson = lessonServices.findById(lessonId). //
                map(lessonInternal -> {
            lessonInternal.setName(lesson.getName());
            return lessonServices.save(lessonInternal, lectorId,facultyId);
        })
                .orElseGet(() -> {
                    lesson.setLessonId(lessonId);
                    return lessonServices.save(lesson, lesson.getLector().getLectorId()
                            ,lesson.getFaculty().getFacultyId());
                });

        EntityModel<Lesson> entityModel = assembler.toModel(updatedLesson);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/restLessons/{lessonId}")
    ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {

        lessonServices.deleteById(lessonId);

        return ResponseEntity.noContent().build();
    }

}

