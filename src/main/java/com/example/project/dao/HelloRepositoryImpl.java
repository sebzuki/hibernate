/*
 * Sébastien Leboucher
 */
package com.example.project.dao;


import com.example.project.dao.domain.Bookmark;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HelloRepositoryImpl implements HelloRepository {
    @PersistenceContext
    private final EntityManager em;

    public HelloRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Bookmark> findAll() {
        return em.createQuery("select distinct bk from Bookmark bk" +
                " LEFT JOIN FETCH bk.owner" +
                " LEFT JOIN FETCH bk.tags" +
                " LEFT JOIN FETCH bk.supports ", Bookmark.class)
        .getResultList();
    }

    @Override
    public void save(String str) {
        //
    }
}