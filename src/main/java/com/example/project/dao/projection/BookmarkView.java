package com.example.project.dao.projection;

import lombok.Getter;

@Getter
public class BookmarkView {

    private long id;
    private String url;
    private String bookmarkName;
    private String ownerName;

    public BookmarkView(long id, String url, String bookmarkName, String ownerName) {
        this.id = id;
        this.url = url;
        this.bookmarkName = bookmarkName;
        this.ownerName = ownerName;
    }
}
