package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.FacultyServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class FacultyController {

    private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);

    @Autowired
    @Qualifier("facultyServices")
    private FacultyServices facultyServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/faculties")
    public ModelAndView getAllFaculties() {
        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/facultyInfo/{facultyId}")
    public ModelAndView getFaculty(@PathVariable("facultyId") Long id) {
        ModelAndView mav = new ModelAndView("faculty/getFaculty");

        mav.addObject("faculty", facultyServices.getById(id));

        return mav;
    }

    @GetMapping("/createFacultyForm")
    public ModelAndView createFacultyForm() {
        ModelAndView mav = new ModelAndView("faculty/createFacultyForm");

        mav.addObject("faculty", new Faculty());

        return mav;
    }

    @PostMapping("/addFaculty")
    public ModelAndView addFaculty(@ModelAttribute @Valid Faculty faculty, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        if (bindingResult.hasErrors()) {
            mav.setViewName("faculty/createFacultyForm");
        } else {
            facultyServices.create(faculty);
            mav.addObject("faculties", facultyServices.getAll());
        }
        return mav;
    }

    @GetMapping(value = "/deleteFaculty/{facultyId}")
    public ModelAndView deleteFaculty(@PathVariable("facultyId") long facultyId) {

        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        facultyServices.deleteById(facultyId);

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/editFaculty/{facultyId}")
    public ModelAndView showUpdateForm(@PathVariable("facultyId") Long facultyId) {

        ModelAndView mav = new ModelAndView("faculty/updateFacultyForm");

        Faculty faculty = facultyServices.getById(facultyId);

        mav.addObject("faculty", faculty);

        return mav;
    }

    @PostMapping("/updateFaculty/{facultyId}")
    public ModelAndView updateFaculty(@PathVariable("facultyId") Long facultyId, @Valid Faculty faculty,
                                      BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView("faculty/allFaculties");
        if (bindingResult.hasErrors()) {
            mav.setViewName("faculty/updateFacultyForm");
        } else {
            facultyServices.update(faculty);
            mav.addObject("faculties", facultyServices.getAll());
        }
        return mav;
    }
}
