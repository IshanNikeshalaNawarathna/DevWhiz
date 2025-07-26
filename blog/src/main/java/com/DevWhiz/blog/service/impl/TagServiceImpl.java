package com.DevWhiz.blog.service.impl;

import com.DevWhiz.blog.domain.entity.Tag;
import com.DevWhiz.blog.repo.TagRepo;
import com.DevWhiz.blog.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;

    @Override
    public List<Tag> getTags() {
        return tagRepo.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTag(Set<String> tagName) {

        List<Tag> existingTag = tagRepo.findByNameIn(tagName);
        Set<String> existingTagName = existingTag.stream()
                .map(tag -> tag.getName()).collect(Collectors.toSet());
        List<Tag> newTag = tagName.stream().filter(name -> !existingTagName.contains(name)).
                map(name -> Tag.builder().name(name).
                        posts(new HashSet<>()).build()).toList();

        List<Tag> saveTag = new ArrayList<>();
        if (!newTag.isEmpty()) {
            saveTag = tagRepo.saveAll(newTag);
        }

        saveTag.addAll(existingTag);

        return saveTag;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepo.findById(id).ifPresent(tag -> {
            if (!tag.getPosts().isEmpty()){
                throw new IllegalArgumentException("Cannot delete tag that has posts");
            }
            tagRepo.deleteById(id);
        });
    }

    @Override
    public Tag findById(UUID id) {
        return tagRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found with id " + id));
    }
}
