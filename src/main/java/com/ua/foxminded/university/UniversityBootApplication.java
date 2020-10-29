package com.ua.foxminded.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)

public class UniversityBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityBootApplication.class, args);
    }

}
