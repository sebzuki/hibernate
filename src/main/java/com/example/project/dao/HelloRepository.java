/*
 * Sébastien Leboucher
 */
package com.example.project.dao;

import com.example.project.dao.domain.Bookmark;

import java.util.List;

public interface HelloRepository {
    List<Bookmark> findAll();

    void save(String str);
}
