package com.example.project.dao;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.domain.Owner;
import com.example.project.dao.domain.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class JpaBookmarkRepositoryTest {
    @Autowired private JpaBookmarkRepository repository;
    @PersistenceContext private EntityManager em;

    @ParameterizedTest
    @MethodSource("bookmark")
    void findAll_should_list_all_bookmarks(Bookmark bookmark, Bookmark bookmark2) {
        repository.saveAll(List.of(bookmark, bookmark2));

        assertThat(repository.findAll())
                .extracting(Bookmark::getName)
                .containsExactlyInAnyOrder(bookmark.getName(), bookmark2.getName());
    }

    static Stream<Arguments> bookmark() {
        return Stream.of(
                arguments(
                        new Bookmark("http://www.junit.org", "JUnit", Set.of(new Tag("test")), new Owner("own1")),
                        new Bookmark("http://www.junit2.org", "JUnit2", Set.of(new Tag("test2")), new Owner("own1"))
                ));
    }
}