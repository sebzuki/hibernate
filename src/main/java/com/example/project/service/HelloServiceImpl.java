/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.HelloRepository;
import com.example.project.dao.JpaSchoolRepository;
import com.example.project.dao.JpaTeacherRepository;
import com.example.project.dao.domain.Director;
import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.domain.Teacher;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import com.example.project.service.mapper.SchoolMapper;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import com.example.project.service.resource.SchoolResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HelloServiceImpl implements HelloService {
    private final HelloRepository helloRepository;
    private final JpaSchoolRepository jpaSchoolRepository;
    private final JpaTeacherRepository jpaTeacherRepository;
    private final SchoolMapper schoolMapper;

    public HelloServiceImpl(HelloRepository helloRepository,
                            JpaSchoolRepository jpaSchoolRepository,
                            JpaTeacherRepository jpaTeacherRepository,
                            SchoolMapper schoolMapper) {
        this.helloRepository = helloRepository;
        this.jpaSchoolRepository = jpaSchoolRepository;
        this.jpaTeacherRepository = jpaTeacherRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    @Transactional
    public void save() {
        for (int i = 0; i < 6; i++) {
            School school = new School("Location"+i, "School"+i,
                    Set.of(new Student("Student" + i)), new Director("Director" + i));
            jpaSchoolRepository.save(school);
            Teacher teacherA = new Teacher("TeacherA" + i, school);
            Teacher teacherB = new Teacher("TeacherB" + i, school);
            jpaTeacherRepository.saveAll(List.of(teacherA, teacherB));
        }
    }

    @Override
    public List<School> findAll() {
        // ici je ne fais pas de mapping donc si les fetch ne sont pas fait, lors du passage dans le controller,
        // je vais un lazy loading exception
        return jpaSchoolRepository.findAllWithGraphAttr();
//        return helloRepository.findAll();
    }

    @Override
    @Transactional
    public CustomPage<SchoolResource> findAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return schoolMapper.mapPage(jpaSchoolRepository.findAllPagination(pageable));
    }

    @Override
    @Transactional
    public CustomSlice<SchoolResource> findAllSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return schoolMapper.mapSlice(jpaSchoolRepository.findAllSlice(pageable));
    }

    @Override
    public List<SchoolView> findWithProjection() {
        return jpaSchoolRepository.findWithProjection();

//        return jpaSchoolRepository.findWithProjectionNative().stream()
//                .map(schoolDTO -> new SchoolView(
//                        schoolDTO.getId(),
//                        schoolDTO.getLocation(),
//                        schoolDTO.getName(),
//                        schoolDTO.getDirectorName()
//                ))
//                .collect(Collectors.toList());
    }

    @Override
    public org.springframework.data.domain.Slice<SchoolView> findWithProjectionSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return jpaSchoolRepository.findWithProjectionSlice(pageable);
    }

    @Override
    public org.springframework.data.domain.Slice<SchoolDTO> findWithProjectionNativeSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return jpaSchoolRepository.findWithProjectionNativeSlice(pageable);
    }
}

