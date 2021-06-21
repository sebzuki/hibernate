/*
 * Sébastien Leboucher
 */
package com.example.project.controller;

import com.example.project.dao.domain.School;
import com.example.project.dao.domain.Student;
import com.example.project.dao.projection.SchoolDTO;
import com.example.project.dao.projection.SchoolView;
import com.example.project.service.SchoolService;
import com.example.project.service.resource.CustomPage;
import com.example.project.service.resource.CustomSlice;
import com.example.project.service.resource.SchoolResource;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Controller {

    private final SchoolService schoolService;

    public Controller(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("save")
    public void save() {
        for (int i = 0; i < 1; i++) {
            schoolService.save(i);
        }
    }

    @GetMapping("findById")
    public Optional<SchoolResource> findById() {
        return schoolService.findById(1L);
    }

    @GetMapping("findAll")
    public List<School> findAll() {
        return schoolService.findAll();
    }

    @GetMapping("findByBatch")
    public List<SchoolResource> findByBatch() {
        return schoolService.findByBatch();
    }

    @GetMapping("findByLocation")
    public List<SchoolResource> findByLocation() {
        return schoolService.findByLocation("ROUEN");
    }

    @GetMapping("findStudentsByLocationAndName")
    public List<Student> findStudentsByLocationAndName() {
        return schoolService.findStudentsByLocationAndName();
    }

    @GetMapping("findAllPagination")
    public CustomPage<SchoolResource> findAllPagination(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "50") int size) {
        return schoolService.findAllPagination(page, size);
    }

    @GetMapping("findAllSlice")
    public CustomSlice<SchoolResource> findAllSlice(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "50") int size) {
        return schoolService.findAllSlice(page, size);
    }

    @GetMapping("findWithProjection")
    public List<SchoolView> findWithProjection() {
        return schoolService.findWithProjection();
    }

    @GetMapping("findWithProjectionSlice")
    public Slice<SchoolView> findWithProjectionSlice(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "50") int size) {
        return schoolService.findWithProjectionSlice(page, size);
    }

    @GetMapping("findWithProjectionNativeSlice")
    public Slice<SchoolDTO> findWithProjectionNativeSlice(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "50") int size) {
        return schoolService.findWithProjectionNativeSlice(page, size);
    }
}
