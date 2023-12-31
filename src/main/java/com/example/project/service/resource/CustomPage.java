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
public class CustomPage<T> {
    private List<T> elements;
    private long totalElements;
    private long totalPages;
    private long offset;
    private int pageNumber;
    private int pageSize;
    private boolean last;
}
