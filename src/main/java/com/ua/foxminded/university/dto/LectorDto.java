package com.ua.foxminded.university.dto;

import com.ua.foxminded.university.model.Faculty;

import java.util.Objects;

public class LectorDto {

    private long lectorId;
    private String firstName;
    private String lastName;

    public LectorDto() {
    }

    public LectorDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LectorDto(long lectorId,  String firstName, String lastName) {
        this.lectorId = lectorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public long getLectorId() {
        return lectorId;
    }

    public void setLectorId(long lectorId) {
        this.lectorId = lectorId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectorDto lectorDto = (LectorDto) o;
        return lectorId == lectorDto.lectorId &&
                Objects.equals(firstName, lectorDto.firstName) &&
                Objects.equals(lastName, lectorDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectorId, firstName, lastName);
    }
}
