package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacultyRepositoryImpl extends CrudRepository<Faculty,Long> {
}


