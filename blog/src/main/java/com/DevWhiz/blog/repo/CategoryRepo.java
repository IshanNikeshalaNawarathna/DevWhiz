package com.DevWhiz.blog.repo;

import com.DevWhiz.blog.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c LEFT JOIN c.posts")
    List<Category> findAllWithPostCount();

    boolean existsByName(String name);

}
