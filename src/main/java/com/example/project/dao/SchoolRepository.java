/*
 * Sébastien Leboucher
 */
package com.example.project.dao;

import com.example.project.dao.domain.School;

import java.util.List;

public interface SchoolRepository {
    List<School> findAllWithCriteriaApi();

    void save(String str);
}
