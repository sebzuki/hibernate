package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "SCHOOL")
@NamedEntityGraph(
        name = "school.full",
        attributeNodes = {@NamedAttributeNode("director"), @NamedAttributeNode("students"), @NamedAttributeNode("teachers")}
)
public class School {

    //    @GenericGenerator(
    //            name = "sequenceGenerator",
    //            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    //            parameters = {
    //                    @Parameter(name = "sequence_name", value = "batch_insert_sequence"),
    //                    @Parameter(name = "optimizer", value = "pooled"),
    //                    @Parameter(name = "initial_value", value = "1"),
    //                    @Parameter(name = "increment_size", value = "100")
    //            }
    //    )
    //    @GeneratedValue(
    //            strategy = GenerationType.SEQUENCE,
    //            generator = "sequenceGenerator"
    //    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String location;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Director director;

    // si la resource Student est du type referentiel ou non en doublon
    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT) // optionel car defaut
    @BatchSize(size = 6) // si je sais combien de relation il peut y avoir, rapide, pas d'impact sur le lazy loading
    @JoinTable(
            name = "SCHOOL_STUDENT",
            joinColumns = @JoinColumn(name = "SCHOOL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    )
    private Set<Student> students;
    // équivalent à ==>
    //    @OneToMany(cascade = CascadeType.PERSIST)
    //    private Set<Student> students


    // si Teacher ne dépend que de school
    // attention, ça génère des doublons sur la requete, necessite un distinct qui sera traité ai niveau du mapping
    // plus compliqué pour la création
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    @Fetch(FetchMode.SUBSELECT)
    // si je ne sais pas combien de relation il peut y avoir, moins rapide, pas d'impact sur le lazy loading
    private Set<Teacher> teachers;

    /**
     * A titre de comparaison, avec 200 000 enregistrements en base
     * Si  @Fetch(FetchMode.SUBSELECT), select all avec pagination => 2s (4 requetes) pour afficher la page 0
     * Si  @BatchSize(size = 6), select all avec pagination => 20ms (4 requetes) pour afficher la page 0
     * Donc pas de pagination avec @Fetch(FetchMode.SUBSELECT) !!!
     */

    public School() {
    }

    public School(String location, String name, Set<Student> students, Director director) {
        this.location = location;
        this.name = name;
        this.students = students;
        this.director = director;
    }
}
