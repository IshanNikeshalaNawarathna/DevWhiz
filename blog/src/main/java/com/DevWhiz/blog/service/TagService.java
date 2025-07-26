package com.DevWhiz.blog.service;

import com.DevWhiz.blog.domain.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<Tag> getTags();
    List<Tag> createTag(Set<String> tagName);
    void deleteTag(UUID id);
    Tag findById(UUID id);

}
