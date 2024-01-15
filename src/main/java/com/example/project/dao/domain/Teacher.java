package com.example.project.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "TEACHER",
        indexes = {
                @Index(name = "school_teacher_index", columnList = "school_id")
        })
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore // beurk resource obligatoire
    @ManyToOne(fetch = FetchType.LAZY) //, optional = false)
    @JoinColumn(name = "school_id") // optionnel
    private School school;

    public Teacher() {
        // for ORM use only
    }

    public Teacher(String name, School school) {
        this.name = name;
        this.school = school;
    }
}
