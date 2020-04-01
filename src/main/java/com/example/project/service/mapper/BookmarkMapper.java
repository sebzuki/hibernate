/*
 * Sébastien Leboucher
 */
package com.example.project.service.mapper;

import com.example.project.dao.domain.Bookmark;
import com.example.project.service.resource.BookmarkResource;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import com.example.project.service.resource.OwnerResource;
import com.example.project.service.resource.SupportResource;
import com.example.project.service.resource.TagResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookmarkMapper {

    public CustomPage<BookmarkResource> mapPage(Page<Bookmark> page) {
        return new CustomPage<BookmarkResource>()
                .setElements(mapResource(page.getContent()))
                .setLast(page.isLast())
                .setOffset(page.getPageable().getOffset())
                .setPageNumber(page.getPageable().getPageNumber())
                .setPageSize(page.getPageable().getPageSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    public CustomSlice<BookmarkResource> mapSlice(Slice<Bookmark> slice) {
        return new CustomSlice<BookmarkResource>()
                .setElements(mapResource(slice.getContent()))
                .setLast(slice.isLast())
                .setOffset(slice.getPageable().getOffset())
                .setPageNumber(slice.getPageable().getPageNumber())
                .setPageSize(slice.getPageable().getPageSize());
    }

    List<BookmarkResource> mapResource(List<Bookmark> bookmarkList) {
        return bookmarkList.stream()
                .map(bookmark -> new BookmarkResource()
                        .setId(bookmark.getId())
                        .setName(bookmark.getName())
                        .setUrl(bookmark.getUrl())
                        .setOwner(new OwnerResource()
                                .setId(bookmark.getOwner().getId())
                                .setName(bookmark.getOwner().getName()))
                        .setTags(bookmark.getTags().stream()
                                .map(tag -> new TagResource()
                                        .setId(tag.getId())
                                        .setName(tag.getName()))
                                .collect(Collectors.toList()))
                        .setSupports(bookmark.getSupports().stream()
                                .map(support -> new SupportResource()
                                        .setId(support.getId())
                                        .setName(support.getName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
