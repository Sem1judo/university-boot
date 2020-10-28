package com.ua.foxminded.university.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="group_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long groupId;


    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @NotBlank(message = "Group name may not be blank")
    @Size(min=2, max=50,
            message="Group name must be between 2 and 50 characters ")
    @Pattern(regexp="^[a-zA-Z0-9-]*$",
            message="Group name must be alphanumeric with no spaces")
    @Column(name="group_name")
    private String name;


    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(Faculty faculty, @NotBlank @Size(min = 3, max = 50,
            message = "Group name must be between 3 and 20 characters ") @Pattern(regexp = "^[a-zA-Z0-9-]*$",
            message = "Group name must be alphanumeric with no spaces") String name) {
        this.faculty = faculty;
        this.name = name;
    }

    public Group(long groupId, Faculty faculty, @NotBlank @Size(min = 3, max = 50,
            message = "Group name must be between 3 and 20 characters ") @Pattern(regexp = "^[a-zA-Z0-9-]*$",
            message = "Group name must be alphanumeric with no spaces") String name) {
        this.groupId = groupId;
        this.faculty = faculty;
        this.name = name;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                Objects.equals(faculty, group.faculty) &&
                Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, faculty, name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", faculty=" + faculty.getName() +
                ", name='" + name + '\'' +
                '}';
    }
}

