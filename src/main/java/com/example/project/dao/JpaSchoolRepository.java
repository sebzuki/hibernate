package com.example.project.dao;

import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSchoolRepository extends JpaRepository<School, String> {
    // 1 seule requete, fait un produit cartesien, super rapide si les grappes sont de tailles résonnables (<100)
    // JPQL pur (Java Persistence Query Language), syntaxe controlée au démarrage, idéal pour récupération d'une seule grappe
    @Query("select distinct sc from School sc" +
            " INNER JOIN FETCH sc.director" +
            " LEFT JOIN FETCH sc.students" +
            " LEFT JOIN FETCH sc.teachers ")
    List<School> findAllWithJPQL();

    // 1 seule requete, syntaxe controlée au runtime
    @EntityGraph(value = "school.full")
    @Query("select sc from School sc")
    List<School> findAllWithGraphName();

    // 1 seule requete, syntaxe controlée au runtime
    // org.hibernate.loader.MultipleBagFetchException si on met plusieurs List dans l'entite au lieu des Set
    @EntityGraph(attributePaths = {"director", "students", "teachers"})
    List<School> findAll();

    // 4 requetes avec le count que l'on peut surcharger
    // pas toute la grappe ici pour répondre au "Hibernate N+1 query problem" avec une requete supplémentaire en mode BatchSize pour chaque OneToMany
    // Sinon erreur : "HHH000104: firstResult/maxResults specified with collection fetch; applying in memory"
    // On ne peut pas mélanger fetch et pagination, sinon on tronquerait les résultats, la BD ne le permet pas
    // le OneToOne ne pose pas de probleme car c'est le même tuple !! Donc il ne casse pas l'optimisation de la base
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

    // Failed to initialize a collection si on oublie l'entity graph !
    // 31ms si on récupère 10 éléments Scool avec tous les joins
    // 36ms si on récupère 10 éléments Scool avec seulement le OneToOne et les BatchSize + SubSelect
    // 50ms sans optim (34 requetes)
    @EntityGraph(attributePaths = {"director", "students", "teachers"})
    List<School> findByLocation(String location);

    @EntityGraph(attributePaths = {"director", "students", "teachers"})
    List<School> findByLocationOrderByName(String location);

    @EntityGraph(attributePaths = {"director", "students", "teachers"})
    @Query(" select sc from School sc " +
           " where sc.location like %:location% " +
           " order by sc.name ")
    List<School> findCustom(@Param("location") String location);

    // Filtrer la sous collection
    @Query("select sc from School sc" +
            " INNER JOIN FETCH sc.director" + // seulement les écoles avec Director
            " LEFT JOIN FETCH sc.students st " +
            " LEFT JOIN FETCH sc.teachers te " +
            " where sc.location like %:location% " +
            " and st.name like %:studentName% " +
            " order by sc.name ")
    List<School> findCustomJoin(
            @Param("location") String location,
            @Param("studentName") String studentName
    );

    // ici je peux renvoyer autre chose que School car ça ne rentre pas en conflit avec les requetes de base qui sont paramétrées
    @Query("select st from School sc" +
            " LEFT JOIN sc.students st " +
            " where sc.location like %:location " +
            " and st.name like :studentName% " +
            " order by st.name ")
    List<Student> findStudentsByLocationAndName(
            @Param("location") String location,
            @Param("studentName") String studentName
    );
}
