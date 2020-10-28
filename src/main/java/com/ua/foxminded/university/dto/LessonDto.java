package com.ua.foxminded.university.dto;


import java.util.Objects;

public class LessonDto {
    private long lessonId;
    private String name;
    private LectorDto lector;

    public LessonDto() {
    }

    public LessonDto(String name, LectorDto lector) {
        this.name = name;
        this.lector = lector;
    }

    public LessonDto(long lessonId, String name, LectorDto lector) {
        this.lessonId = lessonId;
        this.name = name;
        this.lector = lector;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LectorDto getLector() {
        return lector;
    }

    public void setLector(LectorDto lector) {
        this.lector = lector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDto lessonDto = (LessonDto) o;
        return lessonId == lessonDto.lessonId &&
                Objects.equals(name, lessonDto.name) &&
                Objects.equals(lector, lessonDto.lector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, name, lector);
    }

    @Override
    public String toString() {
        return "LessonDto{" +
                "lesson_id=" + lessonId +
                ", name='" + name + '\'' +
                ", lector=" + lector +
                '}';
    }
}

