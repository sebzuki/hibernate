/*
 * Sébastien Leboucher
 */
package com.example.project.service;

import com.example.project.dao.HelloRepository;
import com.example.project.dao.JpaBookmarkRepository;
import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.domain.Owner;
import com.example.project.dao.domain.Tag;
import com.example.project.dao.projection.BookmarkView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class HelloServiceImpl implements HelloService {
    private final HelloRepository helloRepository;
    private final JpaBookmarkRepository repository;

    public HelloServiceImpl(HelloRepository helloRepository, JpaBookmarkRepository repository) {
        this.helloRepository = helloRepository;
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Bookmark> find() {
        repository.saveAll(List.of(
                new Bookmark("http://www.junit.org", "JUnit", Set.of(new Tag("test")), new Owner("own")),
                new Bookmark("http://www.junit2.org", "JUnit2", Set.of(new Tag("test2")), new Owner("own2")))
        );
        return repository.findAll();
    }

    @Override
    public List<Bookmark> findAll() {
        return repository.findAllWithGraphAttr();
        //         return helloRepository.findAll();
    }

    @Override
    public Page<Bookmark> findAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findAllPagination(pageable);
    }

    @Override
    public Slice<Bookmark> findAllSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findAllSlice(pageable);
    }

    @Override
    public List<BookmarkView> findWithOwner() {
        return repository.findWithOwner();

//        return repository.findWithOwnerNative().stream()
//                .map(bookmarkDTO -> new BookmarkView(
//                        bookmarkDTO.getId(),
//                        bookmarkDTO.getUrl(),
//                        bookmarkDTO.getBookmarkName(),
//                        bookmarkDTO.getOwnerName()
//                ))
//                .collect(Collectors.toList());
    }
}

