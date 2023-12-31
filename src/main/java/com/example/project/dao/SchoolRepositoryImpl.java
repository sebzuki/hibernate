/*
 * Sébastien Leboucher
 */
package com.example.project.dao;


import com.example.project.dao.domain.Director;
import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.domain.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SchoolRepositoryImpl implements SchoolRepository {
    private final EntityManager em;

    public SchoolRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    // JPA’s Criteria API
    @Override
    public Optional<School> findById(long id) {
        return Optional.ofNullable(em.find(School.class, id));
    }

    @Override
    public List<School> findAllWithCriteriaApi() {
        return em.createQuery("select distinct sc from School sc" +
                        " LEFT JOIN FETCH sc.director" +
                        " LEFT JOIN FETCH sc.students" +
                        " LEFT JOIN FETCH sc.teachers ", School.class)
                .getResultList();
    }

    @Override
    public void save() {
        // Première sauvegarde en mode grappe
        School school = new School()
                .setLocation("Location")
                .setName("School");

        em.persist(school);

        school.setDirector(new Director("Director"))
                .setStudents(Set.of(new Student("StudentA"), new Student("StudentB")))
                .setTeachers(Set.of(
                        new Teacher().setName("TeacherA").setSchool(school),
                        new Teacher().setName("TeacherB").setSchool(school)));
        // em.detach(school);
        // em.merge(school);
        // em.flush();
    }


    @Override
    public void update() {
        // Pré-requis
        School school = this.findById(1)
                .orElseThrow(() -> new IllegalStateException("Mais vous êtes fou ?? (oh oui !) 90s"));
        school.setName("updated");

        // TEST 1 remplacer OneToOne ?
        Director director = school.getDirector();
        school.setDirector(new Director("DirectorUpdate"));
        em.remove(director);


        // TEST 2 remplacer OneToMAny ?
        em.persist(new Teacher().setName("TeacherC").setSchool(school));

        List<Teacher> teachers = em.createQuery("select tc from Teacher tc where tc.school.id = :schoolId",
                        Teacher.class)
                .setParameter( "schoolId", 1L )
                .getResultList();

        teachers.stream()
                .filter(teacher -> List.of("TeacherA", "TeacherB").contains(teacher.getName()))
                .forEach(em::remove);

        // TEST 3 remplacer ManyToMany ?
        Set<Student> students = school.getStudents();
        List<Student> toDelete = students.stream()
                .filter(student -> "StudentB".equals(student.getName()))
                .collect(Collectors.toList());

        toDelete.forEach(students::remove);
        students.add(new Student("StudentC"));

        em.flush(); // important si on veut supprimer ensuite !
        toDelete.forEach(em::remove);
    }
}
