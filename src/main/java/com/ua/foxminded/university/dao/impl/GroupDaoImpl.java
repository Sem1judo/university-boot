package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.AbstractDaoEntity;
import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Group;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class GroupDaoImpl extends AbstractDaoEntity<Group> implements DaoEntity<Group> {

    public GroupDaoImpl() {
        setClazz(Group.class);
    }
}
