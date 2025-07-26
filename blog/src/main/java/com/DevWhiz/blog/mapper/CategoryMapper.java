package com.DevWhiz.blog.mapper;

import com.DevWhiz.blog.domain.PostStatus;
import com.DevWhiz.blog.domain.dto.*;
import com.DevWhiz.blog.domain.entity.Category;
import com.DevWhiz.blog.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {

        if (posts == null) return 0;
        return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus())).count();

    }

}
