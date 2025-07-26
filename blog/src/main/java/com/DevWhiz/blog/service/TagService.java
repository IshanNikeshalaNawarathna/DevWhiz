package com.DevWhiz.blog.service;

import com.DevWhiz.blog.domain.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    List<Tag> getTags();
    List<Tag> createTag(Set<String> tagName);

}
