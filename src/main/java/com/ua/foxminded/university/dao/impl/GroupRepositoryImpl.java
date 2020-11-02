package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepositoryImpl extends JpaRepository<Group, Long> {
}

