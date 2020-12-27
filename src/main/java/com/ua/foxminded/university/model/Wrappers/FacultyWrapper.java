package com.ua.foxminded.university.model.Wrappers;

import com.ua.foxminded.university.model.Faculty;

import java.util.List;

public class FacultyWrapper {

    private List<Faculty> faculties;

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public FacultyWrapper setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
        return this;
    }
}
