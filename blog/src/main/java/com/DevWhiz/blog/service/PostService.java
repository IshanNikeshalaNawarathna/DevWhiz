package com.DevWhiz.blog.service;

import com.DevWhiz.blog.domain.CreatePostRequest;
import com.DevWhiz.blog.domain.UpdatePostRequest;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getAllDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void  deletePost(UUID id);
}
