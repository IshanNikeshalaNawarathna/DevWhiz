package com.DevWhiz.blog.service;

import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getAllDraftPosts(User user);
}
