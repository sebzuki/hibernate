package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "DIRECTOR")
public class Director {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    private Instant startDate;

    public Director() {
        // for ORM use only
    }

    public Director(String name) {
        this.name = name;
    }

    @PrePersist
    void prePersist() {
        this.startDate = Instant.now();
    }
}
