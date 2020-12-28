package com.ua.foxminded.university.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.foxminded.university.UniversityBootApplication;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.services.FacultyServices;
import com.ua.foxminded.university.services.LectorServices;
import com.ua.foxminded.university.services.LessonServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UniversityBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:testDB.properties")
class LessonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LessonServices lessonServices;
    @Autowired
    private FacultyServices facultyServices;
    @Autowired
    private LectorServices lectorServices;

    @Test
    public void shouldGetListOfLessons() throws Exception {
        List<Lesson> lessons = lessonServices.getAllLight();
        Lesson math = lessons.get(0);
        Lesson technology = lessons.get(1);

        mockMvc.perform(get("/restLessons"))
                .andExpect(status().isOk())
                .andExpect(lesson("$.lessons[0]", math))
                .andExpect(lesson("$.lessons[1]", technology));
    }

    @Test
    public void shouldGetLessonByIdWhenGivenId() throws Exception {

        Lesson lessonEntity = lessonServices.getByIdLight(1);

        mockMvc.perform(get("/restLessons/1"))
                .andExpect(status().isOk())
                .andExpect(lesson("$", lessonEntity));

        assertThat(lessonEntity.getName()).isEqualTo("Math");
    }

    @Test
    public void shouldUpdateAndPutLessonWhenGiveAppropriateLesson() throws Exception {

        Lesson lesson = lessonServices.getByIdLight(8);

        lesson.setName("tesName");

        mockMvc.perform(putJson("/restLessons/8", lesson)
                .param("facultyId", "1")
                .param("lectorId", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(lesson("$", lesson));


        assertThat(lesson.getName()).isEqualTo("tesName");

        assertThat(lesson.getFaculty().getName()).isEqualTo("ITSchool");

        assertThat(lesson.getLector().getFirstName()).isEqualTo("Oleg");
        assertThat(lesson.getLector().getLastName()).isEqualTo("Bilonov");
    }

    @Test
    public void shouldCreateLessonWhenGivenAppropriateLesson() throws Exception {

        Lesson lesson = new Lesson();
        lesson.setName("newOne");


        mockMvc.perform(postJson("/restLessons", lesson)
                .param("facultyId", "1")
                .param("lectorId","1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(lesson("$", lesson));

    }

    public static ResultMatcher lesson(String prefix, Lesson lesson) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".name").value(lesson.getName()));

    }


    public static MockHttpServletRequestBuilder putJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}