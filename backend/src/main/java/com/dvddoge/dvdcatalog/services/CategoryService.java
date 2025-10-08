package com.dvddoge.dvdcatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dvddoge.dvdcatalog.dto.CategoryDTO;
import com.dvddoge.dvdcatalog.entities.Category;
import com.dvddoge.dvdcatalog.repositories.CategoryRepository;
import com.dvddoge.dvdcatalog.services.exceptions.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Categoria inexistente"));
        return new CategoryDTO(entity);
    }
}
