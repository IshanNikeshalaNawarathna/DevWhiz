package com.DevWhiz.blog.controller;

import com.DevWhiz.blog.domain.dtos.CreateTagRequest;
import com.DevWhiz.blog.domain.dtos.TagResponse;
import com.DevWhiz.blog.domain.entity.Tag;
import com.DevWhiz.blog.mapper.TagMapper;
import com.DevWhiz.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;


    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagResponse> list = tags.stream().map(tag-> tagMapper.toDto(tag)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

//    @PostMapping
//    public ResponseEntity<List<TagResponse>> addTag(@RequestBody CreateTagRequest createTagRequest) {
//
//    }

}
