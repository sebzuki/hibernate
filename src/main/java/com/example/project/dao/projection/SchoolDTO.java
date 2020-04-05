package com.example.project.dao.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SchoolDTO {

    @JsonProperty("id")
    long getId();

    @JsonProperty("location")
    String getLocation();

    @JsonProperty("name")
    String getName();

    @JsonProperty("directorName")
    String getDirectorName();
}
