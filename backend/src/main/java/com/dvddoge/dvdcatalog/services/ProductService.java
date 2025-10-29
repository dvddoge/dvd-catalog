package com.dvddoge.dvdcatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dvddoge.dvdcatalog.dto.CategoryDTO;
import com.dvddoge.dvdcatalog.dto.ProductDTO;
import com.dvddoge.dvdcatalog.entities.Product;
import com.dvddoge.dvdcatalog.entities.Category;
import com.dvddoge.dvdcatalog.repositories.CategoryRepository;
import com.dvddoge.dvdcatalog.repositories.ProductRepository;
import com.dvddoge.dvdcatalog.services.exceptions.DatabaseException;
import com.dvddoge.dvdcatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto inexistente"));
        return new ProductDTO(entity, entity.getCategorias());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO atualizarProduto(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id de produto não encontrado.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id de produto não encontrado.");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(entity.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategorias().clear();
        for (CategoryDTO catDto : dto.getCategorias()) {
            Category category = categoryRepository.getReferenceById(catDto.getId());
            entity.getCategorias().add(category);
        }
    }
}
