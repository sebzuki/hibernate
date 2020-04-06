/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import com.example.project.service.resource.SchoolResource;

import java.util.List;

public interface SchoolService {
    void save();

    List<School> findAll();

    List<SchoolView> findWithProjection();

    List<SchoolResource> findBy();

    List<Student> findStudentsByLocationAndName();

    CustomPage<SchoolResource> findAllPagination(int page, int size);

    CustomSlice<SchoolResource> findAllSlice(int page, int size);

    org.springframework.data.domain.Slice<SchoolView> findWithProjectionSlice(int page, int size);

    org.springframework.data.domain.Slice<SchoolDTO> findWithProjectionNativeSlice(int page, int size);
}
