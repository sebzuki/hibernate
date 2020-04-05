package com.example.project.dao.projection;

import lombok.Getter;

@Getter
public class SchoolView {

    private long id;
    private String location;
    private String name;
    private String directorName;

    public SchoolView(long id, String location, String name, String directorName) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.directorName = directorName;
    }
}
