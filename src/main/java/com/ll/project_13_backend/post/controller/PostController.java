package com.ll.project_13_backend.post.controller;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.request.CreatePostRequest;
import com.ll.project_13_backend.post.service.PostService;
import com.ll.project_13_backend.test_security.prinipal.CurrentMember;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                           @CurrentMember Member member) {

        Long postId = postService.createPost(createPostRequest.toCreatePostDto(), member);
        return ResponseEntity.created(URI.create("/post/" + postId)).build();
    }
}