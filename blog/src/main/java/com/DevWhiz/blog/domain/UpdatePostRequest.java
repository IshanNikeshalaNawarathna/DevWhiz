package com.DevWhiz.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {
    private String title;
    private String content;
    private UUID categoryId;
    @Builder.Default
    private Set<UUID> tagIds = new HashSet<>();
    private PostStatus postStatus;
}
