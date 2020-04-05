package com.example.project.dao;

import com.example.project.dao.domain.School;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSchoolRepository extends JpaRepository<School, String> {

    Optional<School> findByLocation(String url);

    // 1 seule requete
    // JPQL pur (Java Persistence Query Language), syntaxe controlée au démérrage
    @Query("select distinct sc from School sc" +
            " LEFT JOIN FETCH sc.director" +
            " LEFT JOIN FETCH sc.students" +
            " LEFT JOIN FETCH sc.teachers ")
    List<School> findAll();

    // 1 seule requete, syntaxe controlée au runtime
    @EntityGraph(value = "school.full")
    @Query("select distinct sc from School sc")
    List<School> findAllWithGraphName();

    // 1 seule requete, syntaxe controlée au runtime
    @EntityGraph(attributePaths = {"director", "students", "teachers"})
    @Query("select distinct sc from School sc")
    List<School> findAllWithGraphAttr();

    // 4 requetes avec le count que l'on peut surcharger
    // pas toute la grappe ici pour répondre au "Hibernate N+1 query problem" avec une requete supplémentaire en mode BatchSize pour chaque OneToMany
    // le OneToOne ne pose pas de probleme car c'est le même tuple !!
    @EntityGraph(attributePaths = {"director"})
    @Query("select sc from School sc")
    Page<School> findAllPagination(Pageable pageable);

    // 3 requetes sans le count
    // pas toute la grappe ici pour répondre au "Hibernate N+1 query problem" avec une requete supplémentaire en mode BatchSize pour chaque OneToMany
    // le OneToOne ne pose pas de probleme car c'est le même tuple !!
    @EntityGraph(attributePaths = {"director"})
    @Query("select sc from School sc")
    Slice<School> findAllSlice(Pageable pageable);

    // 1 seule requete
    // projection classique avec JPQL
    @Query("select new com.example.project.dao.projection.SchoolView(sc.id, sc.location, sc.name, sc.director.name) " +
            "from School sc " +
            "LEFT JOIN sc.director ")
    List<SchoolView> findWithProjection();

    // 1 seule requete
    // projection avec native query et mapping via interface/proxy
    @Query(nativeQuery = true,
            value = "select sc.id as id, sc.location as location, sc.name as name, di.name as directorName " +
                    "from SCHOOL sc left outer join DIRECTOR di on sc.director_id=di.id")
    List<SchoolDTO> findWithProjectionNative();

    // 1 seule requete
    // projection classique avec JPQL + pagination
    @Query("select new com.example.project.dao.projection.SchoolView(sc.id, sc.location, sc.name, sc.director.name) " +
            "from School sc " +
            "LEFT JOIN sc.director ")
    Slice<SchoolView> findWithProjectionSlice(Pageable pageable);

    // 1 seule requete
    // projection avec native query + pagination
    @Query(nativeQuery = true,
            value = "select sc.id as id, sc.location as location, sc.name as name, di.name as directorName " +
                    "from SCHOOL sc left outer join DIRECTOR di on sc.director_id=di.id")
    Slice<SchoolDTO> findWithProjectionNativeSlice(Pageable pageable);
}
