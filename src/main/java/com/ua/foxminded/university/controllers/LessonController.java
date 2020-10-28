package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LessonController {

    @Autowired
    @Qualifier("lessonServices")
    private LessonServices lessonServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/lessons")
    public ModelAndView getAllLessons(Model model) {
        ModelAndView mav = new ModelAndView("lesson/allLessons");

        mav.addObject("lessons", lessonServices.getAll());

        return mav;
    }

    @GetMapping("/lessonProfileLector/{lessonId}")
    public ModelAndView getLesson(@PathVariable("lessonId") Long id) {
        ModelAndView mav = new ModelAndView("lesson/lessonProfileLector");

        mav.addObject("lesson", lessonServices.getById(id));

        return mav;
    }

    @GetMapping("/createLessonForm")
    public ModelAndView createLessonForm() {
        ModelAndView mav = new ModelAndView("lesson/createLessonForm");

        mav.addObject("lesson", new Lesson());

        return mav;
    }

    @PostMapping("/addLesson")
    public ModelAndView addLesson(@ModelAttribute @Valid Lesson lesson,
                                  BindingResult bindingResult,@RequestParam long lectorId) {

        ModelAndView mav = new ModelAndView("lesson/allLessons");

        if (bindingResult.hasErrors()) {
            mav.setViewName("lesson/createLessonForm");
        } else {
            lessonServices.create(lesson, lectorId);
            mav.addObject("lessons", lessonServices.getAll());
        }
        return mav;
    }

    @GetMapping(value = "/deleteLesson/{lessonId}")
    public ModelAndView deleteLesson(@PathVariable("lessonId") long lessonId) {

        ModelAndView mav = new ModelAndView("lesson/allLessons");

        lessonServices.deleteById(lessonId);

        mav.addObject("lessons", lessonServices.getAll());

        return mav;
    }

    @GetMapping("/editLesson/{lessonId}")
    public ModelAndView showUpdateForm(@PathVariable("lessonId") Long lessonId) {

        ModelAndView mav = new ModelAndView("lesson/updateLessonForm");

        Lesson lesson = lessonServices.getByIdLight(lessonId);

        mav.addObject("lesson", lesson);

        return mav;
    }

    @PostMapping("/updateLesson/{lessonId}")
    public ModelAndView updateLesson(@PathVariable("lessonId") Long lessonId,
                                     @Valid Lesson lesson,
                                     BindingResult bindingResult, @RequestParam long lectorId) {

        ModelAndView mav = new ModelAndView("lesson/allLessons");

        if (bindingResult.hasErrors()) {
            mav.setViewName("lesson/updateLessonForm");
        } else {
            lessonServices.update(lesson, lectorId);
            mav.addObject("lessons", lessonServices.getAll());
        }
        return mav;
    }

}
