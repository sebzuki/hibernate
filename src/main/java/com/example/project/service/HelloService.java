/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.domain.BookmarkPage;
import com.example.project.dao.projection.BookmarkView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface HelloService {
    List<Bookmark> find();

    List<Bookmark> findAll();

    List<BookmarkView> findWithProjection();

    Page<BookmarkPage> findAllPagination(int page, int size);

    Slice<BookmarkPage> findAllSlice(int page, int size);

    Slice<BookmarkView> findWithProjectionSlice(int page, int size);
}
