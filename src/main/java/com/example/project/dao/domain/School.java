package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "SCHOOL",
        indexes = {
                @Index(name = "director_school_index", columnList = "director_id"),
                @Index(name = "name_school_index", columnList = "name"),
                @Index(name = "location_school_index", columnList = "location")
})
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location") // optionel si meme nom
    private String location;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "SCHOOL_STUDENT",
            joinColumns = @JoinColumn(name = "SCHOOL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"),
            indexes = {
                    @Index(name = "school_school_student_index", columnList = "school_id"),
                    @Index(name = "student_school_student_index", columnList = "student_id")
            }
    )
    private Set<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school", fetch = FetchType.LAZY)
    private Set<Teacher> teachers;

    @ElementCollection
    private Map<String, String> properties;

    public School() {
        // for ORM use only
    }

    public School(String location, String name, Set<Student> students, Director director) {
        this.location = location;
        this.name = name;
        this.students = students;
        this.director = director;
        // for Example use only
    }
}
