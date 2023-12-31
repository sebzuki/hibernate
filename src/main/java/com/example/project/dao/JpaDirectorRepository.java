package com.example.project.dao;

import com.example.project.dao.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDirectorRepository extends JpaRepository<Director, Long> {
}
