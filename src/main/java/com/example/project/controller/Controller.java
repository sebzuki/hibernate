/*
 * Sébastien Leboucher
 */
package com.example.project.controller;

import com.example.project.dao.domain.School;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import com.example.project.service.HelloService;
import com.example.project.service.resource.SchoolResource;
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

    @GetMapping("save")
    public void save() {
        helloService.save();
    }

    @GetMapping("findAll")
    public List<School> findAll() {
        return helloService.findAll();
    }

    @GetMapping("findAllPagination")
    public CustomPage<SchoolResource> findAllPagination(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "6") int size) {
        return helloService.findAllPagination(page, size);
    }

    @GetMapping("findAllSlice")
    public CustomSlice<SchoolResource> findAllSlice(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size) {
        return helloService.findAllSlice(page, size);
    }

    @GetMapping("findWithProjection")
    public List<SchoolView> findWithProjection() {
        return helloService.findWithProjection();
    }

    @GetMapping("findWithProjectionSlice")
    public Slice<SchoolView> findWithProjectionSlice(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        return helloService.findWithProjectionSlice(page, size);
    }

    @GetMapping("findWithProjectionNativeSlice")
    public Slice<SchoolDTO> findWithProjectionNativeSlice(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "6") int size) {
        return helloService.findWithProjectionNativeSlice(page, size);
    }
}
