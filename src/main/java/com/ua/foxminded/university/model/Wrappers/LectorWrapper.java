package com.ua.foxminded.university.model.Wrappers;

import com.ua.foxminded.university.model.Lector;

import java.util.List;

public class LectorWrapper {

    private List<Lector> lectors;

    public List<Lector> getLectors() {
        return lectors;
    }

    public LectorWrapper setLectors(List<Lector> lectors) {
        this.lectors = lectors;
        return this;
    }
}
