package com.ua.foxminded.university.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.foxminded.university.UniversityBootApplication;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.services.FacultyServices;
import com.ua.foxminded.university.services.GroupServices;
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
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK, classes = UniversityBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:testDB.properties")
class GroupRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupServices groupServices;
    @Autowired
    private FacultyServices facultyServices;


    @Test
    public void shouldGetListOfGroups() throws Exception {
        List<Group> groups = groupServices.getAllLight();
        Group ft14 = groups.get(0);
        Group fk233 = groups.get(1);


        mockMvc.perform(get("/restGroups"))
                .andExpect(status().isOk())
                .andExpect(group("$.groups[0]", ft14))
                .andExpect(group("$.groups[1]", fk233));

    }

    @Test
    public void shouldGetGroupByIdWhenGivenId() throws Exception {


        Group groupEntity = groupServices.getByIdLight(1);

        mockMvc.perform(get("/restGroups/1"))
                .andExpect(status().isOk())
                .andExpect(group("$",groupEntity));

        assertThat(groupEntity.getName()).isEqualTo("FT-14");

    }


    @Test
    public void shouldUpdateAndPutGroupWhenGiveAppropriateGroup() throws Exception {

        Group group = groupServices.getByIdLight(3);

        group.setName("testName");

        mockMvc.perform(putJson("/restGroups/3", group)
                .param("facultyId","1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(group("$", group));

        Group groupEntity = groupServices.getByIdLight(3);
        assertThat(groupEntity.getName()).isEqualTo("testName");

    }
    @Test
    public void shouldCreateGroupWhenGiveAppropriateGroup() throws Exception {

        Group group = new Group();
        group.setName("newOne");

        mockMvc.perform(postJson("/restGroups", group)
                .param("facultyId","1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(group("$", group));

    }

    public static ResultMatcher group(String prefix, Group group) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".name").value(group.getName()));

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