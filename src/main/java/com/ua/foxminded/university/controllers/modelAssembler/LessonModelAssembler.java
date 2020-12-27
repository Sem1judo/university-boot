package com.ua.foxminded.university.controllers.modelAssembler;

import com.ua.foxminded.university.controllers.GroupRestController;
import com.ua.foxminded.university.controllers.LessonRestController;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lesson;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LessonModelAssembler implements RepresentationModelAssembler<Lesson, EntityModel<Lesson>> {

    @Override
    public EntityModel<Lesson> toModel(Lesson lesson) {

        return EntityModel.of(lesson, //
                linkTo(methodOn(LessonRestController.class).one(lesson.getLessonId())).withSelfRel(),
                linkTo(methodOn(LessonRestController.class).allWithHref()).withRel("restLessonsWithHref"));
    }
}