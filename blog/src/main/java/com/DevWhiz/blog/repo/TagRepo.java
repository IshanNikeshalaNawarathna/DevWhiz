package com.DevWhiz.blog.repo;

import com.DevWhiz.blog.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
}
