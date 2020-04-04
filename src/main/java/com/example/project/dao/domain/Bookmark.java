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
@Table(name = "BOOKMARK")
@NamedEntityGraph(
        name = "bookmark.full",
        attributeNodes = {@NamedAttributeNode("owner"), @NamedAttributeNode("tags"), @NamedAttributeNode("supports")}
)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;

    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Owner owner;

    // si la resource Tag est du type referentiel ou non en doublon
    @OneToMany(cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SELECT) // optionel car defaut
    @BatchSize(size = 6)
    @JoinTable(
            name = "BOOKMARK_TAGS",
            joinColumns = @JoinColumn(name = "BOOKMARK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TAGS_ID", referencedColumnName = "ID")
    )
    private Set<Tag> tags;
    // équivalent à ==>
    //    @OneToMany(cascade = CascadeType.PERSIST)
    //    private Set<Tag> tags;


    // si la Support support ne dépend que de bookmark
    // attention, ça génère des doublons sur la requete, necessite un distinct qui sera traité ai niveau du mapping
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookmark")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Support> supports;

    public Bookmark() {
    }

    public Bookmark(String url, String name, Set<Tag> tags, Owner owner) {
        this.url = url;
        this.name = name;
        this.tags = tags;
        this.owner = owner;
    }
}
