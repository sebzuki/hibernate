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
public class CustomSlice<T> {
    private List<T> elements;
    private long offset;
    private int pageNumber;
    private int pageSize;
    private boolean last;
}
