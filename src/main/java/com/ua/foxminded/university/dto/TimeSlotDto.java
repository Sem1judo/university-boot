package com.ua.foxminded.university.dto;

import java.time.LocalDateTime;
import java.util.Objects;


public class TimeSlotDto {

    private Long timeSlotId;
    private LocalDateTime startLesson;
    private LocalDateTime endLesson;
    private LessonDto lessonDto;
    private GroupDto groupDto;


    public TimeSlotDto() {
    }

    public TimeSlotDto(LessonDto lessonDto, GroupDto groupDto) {
        this.lessonDto = lessonDto;
        this.groupDto = groupDto;
    }


    public TimeSlotDto(LocalDateTime startLesson, LocalDateTime endLesson, LessonDto lessonDto, GroupDto groupDto) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.lessonDto = lessonDto;
        this.groupDto = groupDto;
    }


    public TimeSlotDto(Long timeSlotId, LocalDateTime startLesson, LocalDateTime endLesson, LessonDto lessonDto, GroupDto groupDto) {
        this.timeSlotId = timeSlotId;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.lessonDto = lessonDto;
        this.groupDto = groupDto;
    }


    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDateTime getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(LocalDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public LocalDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(LocalDateTime endLesson) {
        this.endLesson = endLesson;
    }

    public LessonDto getLessonDto() {
        return lessonDto;
    }

    public void setLessonDto(LessonDto lessonDto) {
        this.lessonDto = lessonDto;
    }

    public GroupDto getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(GroupDto groupDto) {
        this.groupDto = groupDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlotDto that = (TimeSlotDto) o;
        return Objects.equals(timeSlotId, that.timeSlotId) &&
                Objects.equals(startLesson, that.startLesson) &&
                Objects.equals(endLesson, that.endLesson) &&
                Objects.equals(lessonDto, that.lessonDto) &&
                Objects.equals(groupDto, that.groupDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, startLesson, endLesson, lessonDto, groupDto);
    }
}
