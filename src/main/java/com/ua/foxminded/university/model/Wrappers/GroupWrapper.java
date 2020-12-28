package com.ua.foxminded.university.model.Wrappers;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;


import java.util.List;

public class GroupWrapper {

    private List<Group> groups;


    public List<Group> getGroups() {
        return groups;
    }

    public GroupWrapper setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }
}
