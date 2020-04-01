/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.HelloRepository;
import com.example.project.dao.JpaBookmarkRepository;
import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.domain.Owner;
import com.example.project.dao.domain.Tag;
import com.example.project.dao.projection.BookmarkDTO;
import com.example.project.dao.projection.BookmarkView;
import com.example.project.service.mapper.BookmarkMapper;
import com.example.project.service.resource.BookmarkResource;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class HelloServiceImpl implements HelloService {
    private final HelloRepository helloRepository;
    private final JpaBookmarkRepository repository;
    private final BookmarkMapper bookmarkMapper;

    public HelloServiceImpl(HelloRepository helloRepository, JpaBookmarkRepository repository, BookmarkMapper bookmarkMapper) {
        this.helloRepository = helloRepository;
        this.repository = repository;
        this.bookmarkMapper = bookmarkMapper;
    }

    @Override
    @Transactional
    public List<Bookmark> find() {
        repository.saveAll(List.of(
                new Bookmark("http://www.junit.org", "JUnit",
                        Set.of(new Tag("test")), new Owner("own")),
                new Bookmark("http://www.junit2.org", "JUnit2",
                        Set.of(new Tag("test2")), new Owner("own2")))
        );
        return repository.findAll();
    }

    @Override
    public List<Bookmark> findAll() {
        return repository.findAllWithGraphAttr();
//        return helloRepository.findAll();
    }

    @Override
    @Transactional
    public CustomPage<BookmarkResource> findAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return bookmarkMapper.mapPage(repository.findAllPagination(pageable));
    }

    @Override
    @Transactional
    public CustomSlice<BookmarkResource> findAllSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return bookmarkMapper.mapSlice(repository.findAllSlice(pageable));
    }

    @Override
    public List<BookmarkView> findWithProjection() {
        return repository.findWithProjection();

//        return repository.findWithProjectionNative().stream()
//                .map(bookmarkDTO -> new BookmarkView(
//                        bookmarkDTO.getId(),
//                        bookmarkDTO.getUrl(),
//                        bookmarkDTO.getBookmarkName(),
//                        bookmarkDTO.getOwnerName()
//                ))
//                .collect(Collectors.toList());
    }

    @Override
    public org.springframework.data.domain.Slice<BookmarkView> findWithProjectionSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findWithProjectionSlice(pageable);
    }

    @Override
    public org.springframework.data.domain.Slice<BookmarkDTO> findWithProjectionNativeSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findWithProjectionNativeSlice(pageable);
    }
}

