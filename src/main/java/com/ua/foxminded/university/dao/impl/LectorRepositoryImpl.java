package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectorRepositoryImpl extends JpaRepository<Lector, Long> {
}

