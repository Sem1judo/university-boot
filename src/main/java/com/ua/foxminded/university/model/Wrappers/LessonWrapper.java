package com.ua.foxminded.university.model.Wrappers;

import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.Lesson;

import java.util.List;

public class LessonWrapper {

    private List<Lesson> lessons;

    public List<Lesson> getLessons() {
        return lessons;
    }

    public LessonWrapper setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }
}
