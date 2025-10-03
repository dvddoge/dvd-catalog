package com.dvddoge.dvdcatalog.resources;

import org.springframework.web.bind.annotation.RestController;

import com.dvddoge.dvdcatalog.entities.Category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = new ArrayList<>();   
        list.add(new Category(1L, "Books"));
        list.add(new Category(2L, "Electronics"));
        return ResponseEntity.ok().body(list);
    }
}
