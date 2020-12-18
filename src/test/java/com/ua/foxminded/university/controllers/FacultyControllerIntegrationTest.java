package com.ua.foxminded.university.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.foxminded.university.UniversityBootApplication;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.FacultyServices;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK, classes = UniversityBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:testDB.properties")
class FacultyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FacultyServices facultyServices;




    @Test
    public void shouldGetListOfFaculties() throws Exception {


        List<Faculty> faculties = facultyServices.getAll();
        Faculty facultyZero = faculties.get(0);
        Faculty facultyFirst = faculties.get(1);


        mockMvc.perform(get("/restFaculties"))
                .andExpect(status().isOk())
                .andExpect(faculty("$[0]", facultyZero))
                .andExpect(faculty("$[1]", facultyFirst));

    }

    @Test
    public void shouldGetFacultyByIdWhenGivenId() throws Exception {

        mockMvc.perform(get("/restFaculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ITSchool"));

        Faculty facultyEntity = facultyServices.getById(1);
        assertThat(facultyEntity.getName()).isEqualTo("ITSchool");

    }


    @Test
    public void shouldPutAndCreateFacultyWhenGiveAppropriateFaculty() throws Exception {

        Faculty faculty = new Faculty("ITSchool");

        mockMvc.perform(putJson("/restFaculties/1", faculty))
                .andExpect(status().is2xxSuccessful())
                .andExpect(faculty("$", faculty));

        Faculty facultyEntity = facultyServices.getById(1);
        assertThat(facultyEntity.getName()).isEqualTo("ITSchool");

    }

    @Test
    public void shouldUpdateFacultyWhenGiveAppropriateFaculty() throws Exception {

        Faculty faculty = facultyServices.getById(2);
        faculty.setName("newOne");

        mockMvc.perform(postJson("/restFaculties/2", faculty))
                .andExpect(status().is2xxSuccessful())
                .andExpect(faculty("$", faculty));

        Faculty facultyEntity = facultyServices.getById(1);
        assertThat(facultyEntity.getName()).isEqualTo("newOne");

    }

    public static ResultMatcher faculty(String prefix, Faculty faculty) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".name").value(faculty.getName()));

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

