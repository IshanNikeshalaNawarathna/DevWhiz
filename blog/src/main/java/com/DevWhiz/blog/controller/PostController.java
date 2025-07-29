package com.DevWhiz.blog.controller;

import com.DevWhiz.blog.domain.CreatePostRequest;
import com.DevWhiz.blog.domain.UpdatePostRequest;
import com.DevWhiz.blog.domain.dto.CreatePostRequestDto;
import com.DevWhiz.blog.domain.dto.PostDto;
import com.DevWhiz.blog.domain.dto.UpdatePostRequestDto;
import com.DevWhiz.blog.domain.entity.Post;
import com.DevWhiz.blog.domain.entity.User;
import com.DevWhiz.blog.mapper.PostMapper;
import com.DevWhiz.blog.service.PostService;
import com.DevWhiz.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false) UUID categoryId, @RequestParam(required = false) UUID tagId) {

        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(post -> postMapper.toDto(post)).toList();
        return ResponseEntity.ok(postDtos);

    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDraft(@RequestAttribute UUID id) {
        User loggedInUser = userService.getUserById(id);
        List<Post> draftsPosts = postService.getAllDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftsPosts.stream().map(post -> postMapper.toDto(post)).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto, @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createPostDto = postMapper.toDto(createPost);
        return new ResponseEntity<>(createPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post post = postService.updatePost(id, updatePostRequest);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Void>  deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
