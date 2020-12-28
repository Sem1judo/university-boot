package com.ua.foxminded.university.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.foxminded.university.UniversityBootApplication;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.services.LectorServices;
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
class LectorRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LectorServices lectorServices;

    @Test
    public void shouldGetListOfLectors() throws Exception {
        List<Lector> lectors = lectorServices.getAllLight();
        Lector olegBilonov = lectors.get(0);
        Lector kuzmaPopovich = lectors.get(1);

        mockMvc.perform(get("/restLectors"))
                .andExpect(status().isOk())
                .andExpect(lector("$.lectors[0]", olegBilonov))
                .andExpect(lector("$.lectors[1]", kuzmaPopovich));
    }

    @Test
    public void shouldGetLectorByIdWhenGivenId() throws Exception {

        Lector lectorEntity = lectorServices.getByIdLight(1);

        mockMvc.perform(get("/restLectors/1"))
                .andExpect(status().isOk())
                .andExpect(lector("$",lectorEntity));

        assertThat(lectorEntity.getFirstName()).isEqualTo("Oleg");
        assertThat(lectorEntity.getLastName()).isEqualTo("Bilonov");

    }

    @Test
    public void shouldUpdateAndPutLectorWhenGiveAppropriateLector() throws Exception {

        Lector lector = lectorServices.getByIdLight(3);
        lector.setFirstName("testFirstName");
        lector.setLastName("testLastName");

        mockMvc.perform(putJson("/restLectors/3", lector))
                .andExpect(status().is2xxSuccessful())
                .andExpect(lector("$", lector));

        assertThat(lector.getFirstName()).isEqualTo("testFirstName");
        assertThat(lector.getLastName()).isEqualTo("testLastName");

    }

    @Test
    public void shouldCreateLectorWhenGiveAppropriateLector() throws Exception {

        Lector lector = new Lector();
        lector.setFirstName("newOneFirstName");
        lector.setLastName("newOneLastName");

        mockMvc.perform(postJson("/restLectors", lector))
                .andExpect(status().is2xxSuccessful())
                .andExpect(lector("$", lector));

    }

    public static ResultMatcher lector(String prefix, Lector lector) {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".firstName").value(lector.getFirstName()),
                jsonPath(prefix + ".lastName").value(lector.getLastName()));

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