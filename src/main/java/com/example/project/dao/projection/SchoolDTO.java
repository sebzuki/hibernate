package com.example.project.dao.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public interface SchoolDTO {

    @JsonProperty("id")
    long getId();

    @JsonProperty("location")
    String getLocation();

    @JsonProperty("name")
    String getName();

    @JsonProperty("directorName")
    String getDirectorName();

    @Value("#{ target.name + ' -> ' + target.location }")
    String getNameLocation();
}
