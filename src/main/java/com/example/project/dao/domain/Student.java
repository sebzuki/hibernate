package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Student() {
        // for ORM use only
    }

    public Student(String name) {
        this.name = name;
    }

}
