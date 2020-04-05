/*
 * Sébastien Leboucher
 */
package com.example.project.dao;

import com.example.project.dao.domain.School;

import java.util.List;

public interface HelloRepository {
    List<School> findAll();

    void save(String str);
}
