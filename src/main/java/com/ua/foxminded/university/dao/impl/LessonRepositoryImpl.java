package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepositoryImpl extends JpaRepository<Lesson, Long> {
}

