/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.projection.BookmarkDTO;
import com.example.project.dao.projection.BookmarkView;
import com.example.project.service.resource.BookmarkResource;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;

import java.util.List;

public interface HelloService {
    List<Bookmark> find();

    List<Bookmark> findAll();

    List<BookmarkView> findWithProjection();

    CustomPage<BookmarkResource> findAllPagination(int page, int size);

    CustomSlice<BookmarkResource> findAllSlice(int page, int size);

    org.springframework.data.domain.Slice<BookmarkView> findWithProjectionSlice(int page, int size);

    org.springframework.data.domain.Slice<BookmarkDTO> findWithProjectionNativeSlice(int page, int size);
}
