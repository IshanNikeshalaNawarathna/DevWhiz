package com.DevWhiz.blog.repo;

import com.DevWhiz.blog.domain.PostStatus;
import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {

    List<Post> findAllByPostStatusAndCategoryAndTagsContaining(PostStatus postStatus, Category category, Tag tag);
    List<Post> findAllByPostStatusAndCategory(PostStatus postStatus, Category category);
    List<Post> findAllByPostStatusAndTagsContaining(PostStatus postStatus, Tag tag);
    List<Post> findAllByPostStatus(PostStatus postStatus);

}
