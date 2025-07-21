package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.repo.CategoryRepo;
import com.DevWhiz.blog.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> listCategories() {
        return categoryRepo.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {

        String categoryName = category.getName();
        if (categoryRepo.existsByName(categoryName)) {
            throw new IllegalArgumentException("Category name already exists" + categoryName);
        }
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> byId = categoryRepo.findById(id);
        if(byId.isPresent()) {
            if(!byId.get().getPosts().isEmpty()){
                throw new IllegalStateException("There are posts in this Category");
            }
            categoryRepo.deleteById(id);
        }
    }
}
