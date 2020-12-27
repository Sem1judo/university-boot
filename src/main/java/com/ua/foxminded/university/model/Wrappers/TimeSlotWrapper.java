package com.ua.foxminded.university.model.Wrappers;

import com.ua.foxminded.university.model.TimeSlot;

import java.util.List;

public class TimeSlotWrapper {

    List<TimeSlot> timeSlots;

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public TimeSlotWrapper setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
        return this;
    }
}
