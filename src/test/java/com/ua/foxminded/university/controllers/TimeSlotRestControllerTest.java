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
import java.time.format.DateTimeFormatter;
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
                .andExpect(jsonPath("$.timeSlots[0].startLesson").value("2029-02-03 10:08:02"))
                .andExpect(jsonPath("$.timeSlots[0].endLesson").value("2029-02-03 12:08:02"));

    }

    @Test
    public void shouldGetTimeSlotByIdWhenGivenId() throws Exception {

        TimeSlot timeSlotEntity = timeSlotServices.getByIdLight(7);


        mockMvc.perform(get("/restTimeSlots/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startLesson").value("2029-02-03 10:08:02"))
                .andExpect(jsonPath("$.endLesson").value("2029-02-03 12:08:02"));


        assertThat(timeSlotEntity.getStartLesson())
                .isEqualTo(LocalDateTime.parse("2029-02-03T10:08:02"));
        assertThat(timeSlotEntity.getEndLesson())
                .isEqualTo(LocalDateTime.parse("2029-02-03T12:08:02"));

    }


    @Test
    public void shouldUpdateAndPutTimeSlotWhenGiveAppropriateTimeSlot() throws Exception {

        TimeSlot timeSlot = timeSlotServices.getByIdLight(1);

        timeSlot.setStartLesson(LocalDateTime.of(2025, 10, 13, 15, 0,0));
        timeSlot.setEndLesson(LocalDateTime.of(2025, 10, 13, 17, 0,0));

        mockMvc.perform(putJson("/restTimeSlots/1", timeSlot)
                .param("lessonId", "1")
                .param("groupId", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.startLesson").value("2025-10-13 15:00:00"))
                .andExpect(jsonPath("$.endLesson").value("2025-10-13 17:00:00"));
    }

    @Test
    public void shouldCreateTimeSlotWhenGivenAppropriateTimeSlot() throws Exception {

        TimeSlot timeSlot = new TimeSlot(
                LocalDateTime.parse("2029-02-03T10:08:02"),
                LocalDateTime.parse("2029-02-03T12:08:02"));

        mockMvc.perform(postJson("/restTimeSlots", timeSlot)
                .param("groupId", "1")
                .param("lessonId", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.startLesson").value("2029-02-03 10:08:02"))
                .andExpect(jsonPath("$.endLesson").value("2029-02-03 12:08:02"));

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

