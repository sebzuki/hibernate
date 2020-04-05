/*
 * Sébastien Leboucher
 */
package com.example.project.dao;


import com.example.project.dao.domain.School;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HelloRepositoryImpl implements HelloRepository {
    private final EntityManager em;

    public HelloRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<School> findAll() {
        return em.createQuery("select distinct sc from School sc" +
                " LEFT JOIN FETCH sc.director" +
                " LEFT JOIN FETCH sc.students" +
                " LEFT JOIN FETCH sc.teachers ", School.class)
        .getResultList();
    }

    @Override
    public void save(String str) {
        //
    }
}