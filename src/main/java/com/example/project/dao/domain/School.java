package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Table(name = "SCHOOL",
        indexes = @Index(name = "director_school_index", columnList = "director_id"))
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

    @Column(name = "location") // optionel si meme nom
    private String location;

    @Column(name = "name", length = 100)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Director director;

    // si la resource Student est du type referentiel
    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT) // optionel car defaut
    // si je sais combien de relation il peut y avoir, rapide, pas d'impact sur le lazy loading
    @BatchSize(size = 6)
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
    // équivalent à ==>
    //    @OneToMany(cascade = CascadeType.PERSIST)
    //    private Set<Student> students


    // Si Teacher ne dépend que de school et ne sera pas lié à une autre entité, alors on peut le lier directement
    //     dans la table et économiser une table d'échange !
    // Attention à la boucle infinie sur le mapping JSON (Jackson) si l'entité sort du controler (beurk)
    //     faudra mettre du @JsonIgnore
    // Attention, ça génère des doublons sur la requete si FETCH, necessite un distinct qui sera traité au niveau du mapping
    // Plus compliqué pour la création
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    // Si je ne sais pas combien de relation il peut y avoir, beaucoup moins rapide, pas d'impact sur le lazy loading
    // attention, le subselect a la portée de la requete principale !!!! donc potentiellement sans filtre
    // A ne pas utiliser sans filtre sur la requete principale
    // Clairement pas fait pour la pagination avec grappe de données
    @Fetch(FetchMode.SUBSELECT)
    private Set<Teacher> teachers;

    /**
     * A titre de comparaison sur PGSql, avec 200 000 Schools en base (1 director, 2 teachers, 2 students)
     * Si @Fetch(FetchMode.SUBSELECT), select all avec pagination => 7.6s (4 requetes) pour afficher la page 0 avec 4 éléments
     * Si @BatchSize(size = 6), select all avec pagination => 160ms (4 requetes) pour afficher la même page qu'avant
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
