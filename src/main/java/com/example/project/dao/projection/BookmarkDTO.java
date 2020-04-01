package com.example.project.dao.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface BookmarkDTO {

    @JsonProperty("id")
    long getId();

    @JsonProperty("url")
    String getUrl();

    @JsonProperty("bookmarkName")
    String getBookmarkName();

    @JsonProperty("ownerName")
    String getOwnerName();
}
