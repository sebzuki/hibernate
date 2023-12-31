/*
 * Sébastien Leboucher
 */
package com.example.project.dao;

import com.example.project.dao.domain.School;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository {
    // JPA’s Criteria API
    Optional<School> findById(long id);

    List<School> findAllWithCriteriaApi();

    void save();

    void update();
}
