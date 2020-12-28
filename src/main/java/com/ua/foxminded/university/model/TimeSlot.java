package com.ua.foxminded.university.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Entity
@Table(name="time_slots")
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="timeslot_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long timeSlotId;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="start_lesson")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startLesson;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="end_lesson")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endLesson;


    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;


    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public TimeSlot() {
    }

    public TimeSlot(LocalDateTime startLesson, LocalDateTime endLesson) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public TimeSlot(@NotNull @Future LocalDateTime startLesson, @NotNull @Future LocalDateTime endLesson, Group group, Lesson lesson) {
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.group = group;
        this.lesson = lesson;
    }

    public TimeSlot(long timeSlotId, @NotNull @Future LocalDateTime startLesson, @NotNull @Future LocalDateTime endLesson, Group group, Lesson lesson) {
        this.timeSlotId = timeSlotId;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
        this.group = group;
        this.lesson = lesson;
    }

    public long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(long timeSlotId) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return timeSlotId == timeSlot.timeSlotId &&
                Objects.equals(startLesson, timeSlot.startLesson) &&
                Objects.equals(endLesson, timeSlot.endLesson) &&
                Objects.equals(group, timeSlot.group) &&
                Objects.equals(lesson, timeSlot.lesson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, startLesson, endLesson, group, lesson);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", startLesson=" + startLesson +
                ", endLesson=" + endLesson +
                ", group=" + group +
                ", lesson=" + lesson +
                '}';
    }
}
