package com.DevWhiz.blog.controller;

import com.DevWhiz.blog.domain.dtos.CategoryDto;
import com.DevWhiz.blog.domain.mapper.CategoryMapper;
import com.DevWhiz.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/vi/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategory(){

        List<CategoryDto> categories = categoryService.listCategories()
                .stream().map(category -> mapper.toDto(category))
                .toList();

        return ResponseEntity.ok(categories);
    }

}
