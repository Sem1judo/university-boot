package com.ua.foxminded.university.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculties")
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "faculty_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long facultyId;

    @NotBlank(message = "Faculty name may not be blank")
    @Size(min = 2, max = 50,
            message = "Faculty name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9-]*$",
            message = "Faculty name must be alphanumeric with no spaces")
    @Column(name = "faculty_name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "faculty")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "faculty")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Lesson> lessons = new ArrayList<>();

    public Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(long facultyId, String name, List<Group> groups, List<Lesson> lessons) {
        this.facultyId = facultyId;
        this.name = name;
        this.groups = groups;
        this.lessons = lessons;
    }

    public Faculty(String name, List<Group> groups, List<Lesson> lessons) {
        this.name = name;
        this.groups = groups;
        this.lessons = lessons;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return facultyId == faculty.facultyId &&
                Objects.equals(name, faculty.name) &&
                Objects.equals(groups, faculty.groups) &&
                Objects.equals(lessons, faculty.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, name, groups, lessons);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                ", lessons=" + lessons +
                '}';
    }
}

