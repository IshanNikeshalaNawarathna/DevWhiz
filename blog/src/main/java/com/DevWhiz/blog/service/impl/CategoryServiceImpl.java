package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.repo.CategoryRepo;
import com.DevWhiz.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> listCategories() {
        return categoryRepo.findAllWithPostCount();
    }
}
