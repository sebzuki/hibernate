package com.example.project.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "TEACHER")
public class Teacher {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    @JsonIgnore // beurk resource obligatoire
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id") // optionnel
    private School school;

    public Teacher() {
    }

    public Teacher(String name, School school) {
        this.name = name;
        this.school = school;
    }
}
