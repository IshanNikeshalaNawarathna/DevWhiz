package com.DevWhiz.blog.mapper;

import com.DevWhiz.blog.domain.PostStatus;
import com.DevWhiz.blog.domain.dto.TagDto;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toDto(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {

        if (posts == null) {
            return 0;
        }

        return (int) posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus())).count();

    }

}
