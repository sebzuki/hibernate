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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SchoolService {

    List<School> findAll();

    List<SchoolView> findWithProjection();

    List<SchoolResource> findByBatch();

    List<SchoolResource> findByLocation(String location);

    List<Student> findStudentsByLocationAndName();

    CustomPage<SchoolResource> findAllPagination(int page, int size);

    CustomSlice<SchoolResource> findAllSlice(int page, int size);

    org.springframework.data.domain.Slice<SchoolView> findWithProjectionSlice(int page, int size);

    org.springframework.data.domain.Slice<SchoolDTO> findWithProjectionNativeSlice(int page, int size);

    @Transactional
    void saveMASSE();

    @Transactional
    void saveJPA();

    @Transactional
    void updateJPA();

    @Transactional
    void saveEM();

    @Transactional
    void updateEM();

    Optional<SchoolResource> findById(long id);
}
