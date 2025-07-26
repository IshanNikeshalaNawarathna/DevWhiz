package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.PostStatus;
import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.Tag;
import com.DevWhiz.blog.repo.PostRepo;
import com.DevWhiz.blog.service.CategoryService;
import com.DevWhiz.blog.service.PostService;
import com.DevWhiz.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final CategoryService categoryService;
    private final TagService tagService;

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
}
