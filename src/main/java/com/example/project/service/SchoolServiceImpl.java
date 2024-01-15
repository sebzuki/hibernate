/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.JpaDirectorRepository;
import com.example.project.dao.JpaSchoolRepository;
import com.example.project.dao.JpaStudentRepository;
import com.example.project.dao.JpaTeacherRepository;
import com.example.project.dao.SchoolRepository;
import com.example.project.dao.domain.Director;
import com.example.project.dao.domain.Props;
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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final JpaSchoolRepository jpaSchoolRepository;
    private final JpaTeacherRepository jpaTeacherRepository;
    private final JpaDirectorRepository jpaDirectorRepository;
    private final JpaStudentRepository jpaStudentRepository;
    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(EntityManager em, SchoolRepository schoolRepository,
                             JpaSchoolRepository jpaSchoolRepository,
                             JpaTeacherRepository jpaTeacherRepository,
                             JpaDirectorRepository jpaDirectorRepository, JpaStudentRepository jpaStudentRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.jpaSchoolRepository = jpaSchoolRepository;
        this.jpaTeacherRepository = jpaTeacherRepository;
        this.jpaDirectorRepository = jpaDirectorRepository;
        this.jpaStudentRepository = jpaStudentRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    @Transactional
    public void saveMASSE() {
        for (int i = 0; i < 100000; i++) {
            School school = new School()
                    .setName("School" + i)
                    .setLocation("Location" + i)
                    .setStudents(Set.of(
                            new Student("StudentA" + i),
                            new Student("StudentB" + i)))
                    .setDirector(new Director("Director" + i));
            jpaSchoolRepository.save(school);
            jpaTeacherRepository.saveAll(List.of(
                    new Teacher("TeacherA" + i, school),
                    new Teacher("TeacherB" + i, school)));
        }
    }

    /**
     * AVEC JPA REPOSITORY
     *
     * @return
     */
    @Override
    @Transactional
    public long saveJPA() {
        // Première sauvegarde en mode grappe
        School school = new School()
                .setLocation("Location")
                .setName("School")
                .setProperties(Map.of(Props.SIZE.name(),
                        "10", Props.TYPE.name(),
                        "public", Props.RANK.name(), "1"));

        School managedSchool = jpaSchoolRepository.save(school);    // BONUS saveAndFlush

        return managedSchool.getId();
    }

    @Override
    public void updateJPA(long id) {
    }

    /**
     * AVEC REPOSITORY / ENTITY MANAGER
     **/
    @Override
    @Transactional
    public void saveEM() {
        schoolRepository.save();
    }

    /**
     * AVEC REPOSITORY / ENTITY MANAGER
     **/
    @Override
    @Transactional
    public void updateEM() {
        schoolRepository.update();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolResource> findById(long id) {
        return jpaSchoolRepository.findById(id)
                .map(schoolMapper::mapSchool);
    }

    @Override
    // A noter l'absence de @Transactional ;)
    public List<School> findAll() {
        return jpaSchoolRepository.findAll();
//        return schoolRepository.findAllWithCriteriaApi();
    }


    @Override
    @Transactional
    public List<SchoolResource> findByBatch() {
        return null;
//        return schoolMapper.mapResource(
//                jpaSchoolRepository.findByBatch());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolResource> findByLocation(String location) {
//        Example<School> example = Example.of(
//                new School("Location0", null, null, null),
//                matching().withStringMatcher(StringMatcher.CONTAINING));
//        return schoolMapper.mapResource(
//                jpaSchoolRepository.findAll(example));

//        return schoolMapper.mapResource(
//                jpaSchoolRepository.findByLocationJPQL(location));

        return  null;
    }

    @Override
    public List<Student> findStudentsByLocationAndName() {
        return jpaSchoolRepository.findStudentsByLocationAndName("ocation0", "StudentB");
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPage<SchoolResource> findAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return null;
//        return schoolMapper.mapPage(
//                jpaSchoolRepository.findAllPagination(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomSlice<SchoolResource> findAllSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return  null;

        //return schoolMapper.mapSlice(jpaSchoolRepository.findAllSlice(pageable));
    }

    @Override
    public List<SchoolView> findWithProjection() {
        return  null;
        //return jpaSchoolRepository.findWithProjection(Sort.by(ASC, "name"));

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
        return  null;
        //return jpaSchoolRepository.findWithProjectionSlice(pageable);
    }

    @Override
    public org.springframework.data.domain.Slice<SchoolDTO> findWithProjectionNativeSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return  null;
        //return jpaSchoolRepository.findWithProjectionNativeSlice(pageable);
    }
}

