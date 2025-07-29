package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.PostStatus;
import com.DevWhiz.blog.domain.CreatePostRequest;
import com.DevWhiz.blog.domain.UpdatePostRequest;
import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.Tag;
import com.DevWhiz.blog.domain.entity.User;
import com.DevWhiz.blog.repo.PostRepo;
import com.DevWhiz.blog.service.CategoryService;
import com.DevWhiz.blog.service.PostService;
import com.DevWhiz.blog.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Override
    public Post getPost(UUID id) {
        return postRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Post does not exist with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null) {

            Category category = categoryService.findById(categoryId);
            Tag tag = tagService.findById(tagId);

            return postRepo.findAllByPostStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );

        }

        if (categoryId != null) {
            Category category = categoryService.findById(categoryId);
            return postRepo.findAllByPostStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }
        if (tagId != null) {
            Tag tag = tagService.findById(tagId);
            return postRepo.findAllByPostStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepo.findAllByPostStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getAllDraftPosts(User user) {
        return postRepo.findAllByAuthorAndPostStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {

        Post post = new Post();
        post.setTitle(createPostRequest.getTitle());
        post.setAuthor(user);
        post.setContent(createPostRequest.getContent());
        post.setPostStatus(createPostRequest.getPostStatus());
        post.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.findById(createPostRequest.getCategoryId());

        post.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);

        post.setTags(new HashSet<>(tags));

        return postRepo.save(post);

    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {

        Post existingPost = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Post does not exist with id: " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setPostStatus(updatePostRequest.getPostStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

        UUID categoryId = updatePostRequest.getCategoryId();
        if(!existingPost.getCategory().getId().equals(categoryId)) {
            Category category = categoryService.findById(categoryId);
            existingPost.setCategory(category);
        }

        Set<UUID> existingTagId = existingPost.getTags().stream().map(tag -> tag.getId()).collect(Collectors.toSet());
        Set<UUID> tagIds = updatePostRequest.getTagIds();

        if (!existingTagId.equals(tagIds)) {
            List<Tag> newTag = tagService.getTagByIds(tagIds);
            existingPost.setTags(new HashSet<>(newTag));
        }

        return postRepo.save(existingPost);

    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepo.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;
        return  (int) Math.ceil ((double) wordCount / WORDS_PER_MINUTE);
    }


}
