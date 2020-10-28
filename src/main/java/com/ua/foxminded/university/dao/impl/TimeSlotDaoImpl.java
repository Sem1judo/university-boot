package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.TimeSlot;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class TimeSlotDaoImpl extends AbstractDaoEntity<TimeSlot> implements DaoEntity<TimeSlot> {

    public TimeSlotDaoImpl() {
        setClazz(TimeSlot.class);
    }
}

