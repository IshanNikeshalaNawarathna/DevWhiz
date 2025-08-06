package com.DevWhiz.blog.mapper;

import com.DevWhiz.blog.domain.CreatePostRequest;
import com.DevWhiz.blog.domain.UpdatePostRequest;
import com.DevWhiz.blog.domain.dto.CreatePostRequestDto;
import com.DevWhiz.blog.domain.dto.PostDto;
import com.DevWhiz.blog.domain.dto.UpdatePostRequestDto;
import com.DevWhiz.blog.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest  toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);

}
