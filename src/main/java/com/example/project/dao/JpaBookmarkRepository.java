package com.example.project.dao;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.projection.BookmarkDTO;
import com.example.project.dao.projection.BookmarkView;
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
public interface JpaBookmarkRepository extends JpaRepository<Bookmark, String> {

    Optional<Bookmark> findByUrl(String url);

    @Query("select distinct bk from Bookmark bk" +
            " LEFT JOIN FETCH bk.owner" +
            " LEFT JOIN FETCH bk.tags" +
            " LEFT JOIN FETCH bk.supports ")
    List<Bookmark> findAll();

    @EntityGraph(value = "bookmark.full")
    @Query("select distinct bk from Bookmark bk")
    List<Bookmark> findAllWithGraphName();

    @EntityGraph(attributePaths = {"owner", "tags", "supports"})
    @Query("select distinct bk from Bookmark bk")
    List<Bookmark> findAllWithGraphAttr();

    @EntityGraph(attributePaths = {"owner", "supports"})
    @Query("select distinct bk from Bookmark bk")
    Page<Bookmark> findAllPagination(Pageable pageable);

    @EntityGraph(attributePaths = {"owner", "supports"})
    @Query("select distinct bk from Bookmark bk")
    Slice<Bookmark> findAllSlice(Pageable pageable);

    @Query("select new com.example.project.dao.projection.BookmarkView(bk.id, bk.url, bk.name, bk.owner.name) " +
            "from Bookmark bk " +
            "LEFT JOIN bk.owner ")
    List<BookmarkView> findWithOwner();

    @Query(nativeQuery = true,
            value = "select bm.id as id, bm.url as url, bm.name as bookmarkName, ow.name as ownerName " +
                    "from BOOKMARK bm left outer join OWNER ow on bm.owner_id=ow.id")
    List<BookmarkDTO> findWithOwnerNative();
}
