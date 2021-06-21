package com.example.project.dao;

import com.example.project.dao.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStudentRepository extends JpaRepository<Student, Long> {
}
