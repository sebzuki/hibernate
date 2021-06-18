package com.example.project.dao;

import com.example.project.dao.domain.Director;
import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
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
class JpaSchoolRepositoryTest {
    @Autowired private JpaSchoolRepository repository;
    @PersistenceContext private EntityManager em;

    @ParameterizedTest
    @MethodSource("school")
    void findAll_should_list_all_schools(School school, School school2) {
        repository.saveAll(List.of(school, school2));

        assertThat(repository.findAll())
                .extracting(School::getName)
                .containsExactlyInAnyOrder(school.getName(), school2.getName());
    }

    static Stream<Arguments> school() {
        return Stream.of(
                arguments(
                        new School("http://www.junit.org", "JUnit", Set.of(new Student("test")), new Director("own1")),
                        new School("http://www.junit2.org", "JUnit2", Set.of(new Student("test2")), new Director("own1"))
                ));
    }
}
