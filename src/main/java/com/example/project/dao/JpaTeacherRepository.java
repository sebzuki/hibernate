package com.example.project.dao;

import com.example.project.dao.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTeacherRepository extends JpaRepository<Teacher, Long> {
}
