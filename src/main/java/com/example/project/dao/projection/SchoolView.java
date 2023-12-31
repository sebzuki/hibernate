package com.example.project.dao.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolView {

    private final long id;
    private final String location;
    private final String name;
    private final String directorName;
}
