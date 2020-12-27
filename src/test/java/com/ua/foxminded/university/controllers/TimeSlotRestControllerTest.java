package com.ua.foxminded.university.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.foxminded.university.UniversityBootApplication;
import com.ua.foxminded.university.model.*;
import com.ua.foxminded.university.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
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
class TimeSlotRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TimeSlotServices timeSlotServices;
    @Autowired
    private LessonServices lessonServices;
    @Autowired
    private GroupServices groupServices;


    @Test
    public void shouldGetListOfTimeSlots() throws Exception {
        List<TimeSlot> timeSlots = timeSlotServices.getAllLight();

        mockMvc.perform(get("/restTimeSlots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeSlots[0].startLesson").value("2021-10-13 15:00:00"))
                .andExpect(jsonPath("$.timeSlots[0].endLesson").value("2021-10-13 17:00:00"));


    }

    @Test
    public void shouldGetTimeSlotByIdWhenGivenId() throws Exception {

        TimeSlot timeSlotEntity = timeSlotServices.getByIdLight(1);

        mockMvc.perform(get("/restTimeSlots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startLesson").value("2021-10-13 15:00:00"))
                .andExpect(jsonPath("$.endLesson").value("2021-10-13 17:00:00"));


        assertThat(timeSlotEntity.getStartLesson())
                .isEqualTo(LocalDateTime.of(2021, 10, 13, 15, 0));
        assertThat(timeSlotEntity.getEndLesson())
                .isEqualTo(LocalDateTime.of(2021, 10, 13, 17, 0));

    }


    @Test
    public void shouldUpdateAndPutTimeSlotWhenGiveAppropriateTimeSlot() throws Exception {

        TimeSlot timeSlot = timeSlotServices.getByIdLight(1);

        mockMvc.perform(putJson("/restTimeSlots/1", timeSlot)
                .param("lessonId", "1")
                .param("groupId", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.startLesson").value("2021-10-13T15:00:00"))
                .andExpect(jsonPath("$.endLesson").value("2021-10-13T17:00:00"));

    }

    @Test
    public void shouldCreateTimeSlotWhenGivenAppropriateTimeSlot() throws Exception {

        TimeSlot timeSlot = new TimeSlot(
                LocalDateTime.of(2221, 10, 13, 15, 0),
                LocalDateTime.of(2221, 10, 13, 17, 0));


        mockMvc.perform(postJson("/restTimeSlots", timeSlot)
                .param("groupId", "1")
                .param("lessonId", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.startLesson").value("2221-10-13T15:00:00"))
                .andExpect(jsonPath("$.endLesson").value("2221-10-13T17:00:00"));

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