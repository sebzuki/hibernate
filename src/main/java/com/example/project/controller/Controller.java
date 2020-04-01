/*
 * Sébastien Leboucher
 */
package com.example.project.controller;

import com.example.project.dao.domain.Bookmark;
import com.example.project.dao.projection.BookmarkDTO;
import com.example.project.dao.projection.BookmarkView;
import com.example.project.service.HelloService;
import com.example.project.service.resource.BookmarkResource;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final HelloService helloService;

    public Controller(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("find")
    List<Bookmark> find() {
        return helloService.find();
    }

    @GetMapping("findAll")
    List<Bookmark> findAll() {
        return helloService.findAll();
    }

    @GetMapping("findAllPagination")
    CustomPage<BookmarkResource> findAllPagination(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "6") int size) {
        return helloService.findAllPagination(page, size);
    }

    @GetMapping("findAllSlice")
    CustomSlice<BookmarkResource> findAllSlice(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "6") int size) {
        return helloService.findAllSlice(page, size);
    }

    @GetMapping("findWithProjection")
    List<BookmarkView> findWithProjection() {
        return helloService.findWithProjection();
    }

    @GetMapping("findWithProjectionSlice")
    Slice<BookmarkView> findWithProjectionSlice(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "6") int size) {
        return helloService.findWithProjectionSlice(page, size);
    }

    @GetMapping("findWithProjectionNativeSlice")
    Slice<BookmarkDTO> findWithProjectionNativeSlice(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return helloService.findWithProjectionNativeSlice(page, size);
    }
}
