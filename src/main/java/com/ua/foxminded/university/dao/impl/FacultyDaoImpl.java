package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Faculty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class FacultyDaoImpl extends AbstractDaoEntity<Faculty> implements DaoEntity<Faculty> {

    public FacultyDaoImpl() {
        setClazz(Faculty.class);
    }
}
