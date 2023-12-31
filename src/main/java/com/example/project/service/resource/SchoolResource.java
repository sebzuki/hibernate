/*
 * Sébastien Leboucher
 */
package com.example.project.service.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SchoolResource {
    private long id;
    private String location;
    private String name;
    private DirectorResource director;
    private List<StudentResource> students;
    private List<TeacherResource> teachers;
}
