/*
 * Sébastien Leboucher
 */
package com.example.project.service.mapper;

import com.example.project.dao.domain.School;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import com.example.project.service.resource.DirectorResource;
import com.example.project.service.resource.SchoolResource;
import com.example.project.service.resource.StudentResource;
import com.example.project.service.resource.TeacherResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchoolMapper {

    public CustomPage<SchoolResource> mapPage(Page<School> page) {
        return new CustomPage<SchoolResource>()
                .setElements(mapResource(page.getContent()))
                .setLast(page.isLast())
                .setOffset(page.getPageable().getOffset())
                .setPageNumber(page.getPageable().getPageNumber())
                .setPageSize(page.getPageable().getPageSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    public CustomSlice<SchoolResource> mapSlice(Slice<School> slice) {
        return new CustomSlice<SchoolResource>()
                .setElements(mapResource(slice.getContent()))
                .setLast(slice.isLast())
                .setOffset(slice.getPageable().getOffset())
                .setPageNumber(slice.getPageable().getPageNumber())
                .setPageSize(slice.getPageable().getPageSize());
    }

    public List<SchoolResource> mapResource(List<School> schoolList) {
        return schoolList.stream()
                .map(this::mapSchool)
                .collect(Collectors.toList());
    }

    public SchoolResource mapSchool(School school) {
        return new SchoolResource()
                .setId(school.getId())
                .setName(school.getName())
                .setLocation(school.getLocation())
                .setDirector(new DirectorResource()
                        .setId(school.getDirector().getId())
                        .setName(school.getDirector().getName()))
                .setStudents(school.getStudents().stream()
                        .map(student -> new StudentResource()
                                .setId(student.getId())
                                .setName(student.getName()))
                        .collect(Collectors.toList()))
                .setTeachers(school.getTeachers().stream()
                        .map(teacher -> new TeacherResource()
                                .setId(teacher.getId())
                                .setName(teacher.getName()))
                        .collect(Collectors.toList()));
    }
}
