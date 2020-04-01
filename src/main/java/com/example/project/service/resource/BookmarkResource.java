/*
 * Sébastien Leboucher
 */
package com.example.project.service.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class BookmarkResource {
    private long id;
    private String url;
    private String name;
    private OwnerResource owner;
    private List<TagResource> tags;
    private List<SupportResource> supports;
}
