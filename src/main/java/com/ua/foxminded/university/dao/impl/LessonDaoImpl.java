package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Lesson;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class LessonDaoImpl extends AbstractDaoEntity<Lesson> implements DaoEntity<Lesson> {

    public LessonDaoImpl() {
        setClazz(Lesson.class);
    }
}
