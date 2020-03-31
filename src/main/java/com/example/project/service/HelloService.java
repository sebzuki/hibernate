/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.projection.BookmarkView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface HelloService {
    List<Bookmark> find();

    List<Bookmark> findAll();

    Slice<Bookmark> findAllSlice(int page, int size);

    List<BookmarkView> findWithOwner();

    Page<Bookmark> findAllPagination(int page, int size);
}
