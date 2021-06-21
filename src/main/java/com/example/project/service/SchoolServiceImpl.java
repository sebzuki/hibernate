/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.JpaSchoolRepository;
import com.example.project.dao.JpaTeacherRepository;
import com.example.project.dao.SchoolRepository;
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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final EntityManager em;
    private final SchoolRepository schoolRepository;
    private final JpaSchoolRepository jpaSchoolRepository;
    private final JpaTeacherRepository jpaTeacherRepository;
    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(EntityManager em, SchoolRepository schoolRepository,
                             JpaSchoolRepository jpaSchoolRepository,
                             JpaTeacherRepository jpaTeacherRepository,
                             SchoolMapper schoolMapper) {
        this.em = em;
        this.schoolRepository = schoolRepository;
        this.jpaSchoolRepository = jpaSchoolRepository;
        this.jpaTeacherRepository = jpaTeacherRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    @Transactional
    public void save(int j) {
        for (int i = 0; i < 6; i++) {
            School school = new School("Location" + i+j, "School" + i+j, Set.of(
                    new Student("StudentA" + i+j),
                    new Student("StudentB" + i+j)),
                    new Director("Director" + i+j));
            jpaSchoolRepository.save(school);
            jpaTeacherRepository.saveAll(List.of(
                    new Teacher("TeacherA" + i+j, school),
                    new Teacher("TeacherB" + i+j, school)));

//            em.persist(school);
//            em.persist(new Teacher("TeacherA" + i, school));
//            em.persist(new Teacher("TeacherB" + i, school));
        }
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
        // ici je ne fais pas de mapping donc si les fetch ne sont pas fait, lors du passage dans le controller,
        // je vais un lazy loading exception
        return jpaSchoolRepository.findAll();
//        return schoolRepository.findAllWithCriteriaApi();
    }


    @Override
    @Transactional
    public List<SchoolResource> findByBatch() {
        return schoolMapper.mapResource(
                jpaSchoolRepository.findByBatch());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolResource> findByLocation(String location) {
//        Example<School> example = Example.of(
//                new School("Location0", null, null, null),
//                matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return schoolMapper.mapResource(
                jpaSchoolRepository.findByLocationCustom(location));
    }

    @Override
    public List<Student> findStudentsByLocationAndName() {
        return jpaSchoolRepository.findStudentsByLocationAndName("ocation0", "StudentB");
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPage<SchoolResource> findAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return schoolMapper.mapPage(
                jpaSchoolRepository.findAllPagination(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomSlice<SchoolResource> findAllSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return schoolMapper.mapSlice(
                jpaSchoolRepository.findAllSlice(pageable));
    }

    @Override
    public List<SchoolView> findWithProjection() {
        return jpaSchoolRepository.findWithProjection(Sort.by(ASC, "name"));

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

