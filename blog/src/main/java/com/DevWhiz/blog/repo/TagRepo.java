package com.DevWhiz.blog.repo;

import com.DevWhiz.blog.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts")
    List<Tag> findAllWithPostCount();

    List<Tag> findByNameIn(Set<String> names);

}
