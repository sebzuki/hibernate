package com.example.project.dao;

import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaSchoolRepository extends JpaRepository<School, Long> {

    //Optional<School> findById(long id);

    //List<School> findAllWithJPQL();

    //List<School> findAllWithGraphName();

    List<School> findAll();

    //List<School> findByBatch();

    //Page<School> findAllPagination(Pageable pageable);

    //Slice<School> findAllSlice(Pageable pageable);

    //List<SchoolView> findWithProjection(Sort sort);

    //List<SchoolDTO> findWithProjectionNative();

    //Slice<SchoolView> findWithProjectionSlice(Pageable pageable);

    //Slice<SchoolDTO> findWithProjectionNativeSlice(Pageable pageable);

    List<School> findByLocationOrderByName(String location);

    //List<School> findByLocationCustom(@Param("location") String location);

    //List<School> findByLocationJPQL(@Param("location") String location);

    List<Student> findStudentsByLocationAndName(
            @Param("location") String location,
            @Param("studentName") String studentName
    );
}
