package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.services.LectorServices;
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
public class LectorController {

    @Autowired
    @Qualifier("lectorServices")
    private LectorServices lectorServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/lectors")
    public ModelAndView getaAllLectors(Model model) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }

    @GetMapping("/lectorInfo/{lectorId}")
    public ModelAndView getLector(@PathVariable("lectorId") Long id) {
        ModelAndView mav = new ModelAndView("lector/getLector");

        mav.addObject("lectorFaculty", lectorServices.getById(id));

        return mav;
    }

    @GetMapping("/createLectorForm")
    public ModelAndView createLectorForm() {
        ModelAndView mav = new ModelAndView("lector/createLectorForm");

        mav.addObject("lector", new Lector());

        return mav;
    }

    @PostMapping("/addLector")
    public ModelAndView addLector(@ModelAttribute @Valid Lector lector,
                                 BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        if (bindingResult.hasErrors()) {
            mav.setViewName("lector/createLectorForm");
        } else {
            lectorServices.create(lector);
            mav.addObject("lectors", lectorServices.getAll());
        }
        return mav;
    }

    @GetMapping(value = "/deleteLector/{lectorId}")
    public ModelAndView deleteLector(@PathVariable("lectorId") long lectorId) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        lectorServices.deleteById(lectorId);

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }

    @GetMapping("/editLector/{lectorId}")
    public ModelAndView showUpdateForm(@PathVariable("lectorId") Long lectorId) {

        ModelAndView mav = new ModelAndView("lector/updateLectorForm");

        Lector lector = lectorServices.getByIdLight(lectorId);

        mav.addObject("lector", lector);

        return mav;
    }

    @PostMapping("/updateLector/{lectorId}")
    public ModelAndView updateLector(@PathVariable("lectorId") Long groupId,
                                     @Valid Lector lector ,
                                     BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        if (bindingResult.hasErrors()) {
            mav.setViewName("lector/updateLectorForm");
        } else {

            lectorServices.update(lector);
            mav.addObject("lectors", lectorServices.getAll());
        }
        return mav;
    }
}
