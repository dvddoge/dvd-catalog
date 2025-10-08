package com.dvddoge.dvdcatalog.resources;

import org.springframework.web.bind.annotation.RestController;

import com.dvddoge.dvdcatalog.dto.CategoryDTO;
import com.dvddoge.dvdcatalog.entities.Category;
import com.dvddoge.dvdcatalog.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categorias = service.findAll();
        return ResponseEntity.ok().body(categorias);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO categoria = service.findById(id);
        return ResponseEntity.ok().body(categoria);
    }
}
