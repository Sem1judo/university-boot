package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Lector;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LectorDaoImpl extends AbstractDaoEntity<Lector> implements DaoEntity<Lector> {

    public LectorDaoImpl() {
        setClazz(Lector.class);
    }
}
