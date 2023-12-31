/*
 * Sébastien Leboucher
 */
package com.example.project.service.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TeacherResource {
    private long id;
    private String name;
}
