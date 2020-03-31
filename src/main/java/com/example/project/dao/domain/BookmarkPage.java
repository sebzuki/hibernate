package com.example.project.dao.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "BOOKMARK")
public class BookmarkPage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;

    // si la resource Tag est du type referentiel ou non en doublon
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @BatchSize(size = 6)
    @JoinTable(
            name = "BOOKMARK_TAGS",
            joinColumns = @JoinColumn(name = "BOOKMARK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TAGS_ID", referencedColumnName = "ID")
    )
    private Set<Tag> tags;
    // équivalent à ==>
    //    @OneToMany(cascade = CascadeType.ALL)
    //    private Set<Tag> tags;


    // si la Support support ne dépend que de bookmark
    // attention, ça génère des doublons sur la requete, necessite un distinct qui sera traité ai niveau du mapping
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookmark", fetch = FetchType.EAGER)
    @BatchSize(size = 6)
    private Set<Support> supports;

    public BookmarkPage() {
    }

    public BookmarkPage(String url, String name, Set<Tag> tags, Owner owner) {
        this.url = url;
        this.name = name;
        this.tags = tags;
        this.owner = owner;
    }
}
