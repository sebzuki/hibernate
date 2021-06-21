package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
        indexes = {
                @Index(name = "director_school_index", columnList = "director_id"),
                @Index(name = "name_school_index", columnList = "name"),
                @Index(name = "location_school_index", columnList = "location")
        })
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location") // optionel si meme nom
    private String location;

    @Column(name = "name", length = 100)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    // si la resource Student est du type referentiel
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT) // optionel car defaut
    // si je sais combien de relation il peut y avoir, rapide, pas d'impact sur le lazy loading : oblige à savoir !
    @BatchSize(size = 50)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school", fetch = FetchType.LAZY)
    // Si je ne sais pas combien de relation il peut y avoir, beaucoup moins rapide, pas d'impact sur le lazy loading
    // attention, le subselect a la portée de la requete principale !!!! donc potentiellement sans filtre
    // A ne pas utiliser sans filtre sur la requete principale
    // Clairement pas fait pour la pagination avec grappe de données
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 50)
    private Set<Teacher> teachers;

    /**
     * A titre de comparaison sur PGSql, avec 200 000 Schools en base (1 director, 2 teachers, 2 students)
     * Si @Fetch(FetchMode.SUBSELECT), select all avec pagination => 7.6s (4 requetes) pour afficher la page 0 avec 4 éléments
     *    (presque 11s si on met des UUID à la place des long
     * Si @BatchSize(size = 6), select all avec pagination => 160ms (4 requetes) pour afficher la même page qu'avant
     * Donc pas de pagination avec @Fetch(FetchMode.SUBSELECT) !!!
     *
     * Le remplacement de long par UUID a un impact important sur la taille de la BD : 590Mo -> 790Mo pour 1 000 000 d'enregistrements d'une table de 4 colonnes
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
