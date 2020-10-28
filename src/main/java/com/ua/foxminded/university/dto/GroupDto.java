package com.ua.foxminded.university.dto;

import com.ua.foxminded.university.model.Faculty;

import java.util.Objects;


public class GroupDto {

    private long groupId;
    private Faculty faculty;
    private String name;

    public GroupDto() {
    }

    public GroupDto(String name) {
        this.name = name;
    }

    public GroupDto(long groupId, Faculty faculty, String name) {
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
        GroupDto groupDto = (GroupDto) o;
        return groupId == groupDto.groupId &&
                Objects.equals(faculty, groupDto.faculty) &&
                Objects.equals(name, groupDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, faculty, name);
    }
}
