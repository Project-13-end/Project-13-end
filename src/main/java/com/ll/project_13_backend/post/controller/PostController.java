package com.ll.project_13_backend.post.controller;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.request.CreatePostRequest;
import com.ll.project_13_backend.post.dto.request.UpdatePostRequest;
import com.ll.project_13_backend.post.dto.response.FindAllPostResponse;
import com.ll.project_13_backend.post.dto.response.FindPostResponse;
import com.ll.project_13_backend.post.service.PostService;
import com.ll.project_13_backend.test_security.prinipal.CurrentMember;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Slice<FindAllPostResponse>> findAll(@RequestParam(defaultValue = "0") int page) {
        Slice<FindAllPostResponse> findAllPostResponses = FindAllPostResponse.of(postService.findAllPost(page));
        return ResponseEntity.ok(findAllPostResponses);
    }

    @GetMapping("search")
    public ResponseEntity<Slice<FindAllPostResponse>> findSearchAll(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "") String keyword) {
        Slice<FindAllPostResponse> findAllPostResponseSlice =
                FindAllPostResponse.of(postService.findAllPostByKeyword(page, keyword));
        return ResponseEntity.ok(findAllPostResponseSlice);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                           @CurrentMember Member member) {

        Long postId = postService.createPost(createPostRequest.toCreatePostDto(), member);
        return ResponseEntity.created(URI.create("/post/" + postId)).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPost(@PathVariable("postId") Long postId) {

        FindPostResponse findPostResponse = FindPostResponse.of(postService.findPost(postId));
        return ResponseEntity.ok(findPostResponse);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable("postId") Long postId,
                                           @Valid @RequestBody UpdatePostRequest updatePostRequest,
                                           @CurrentMember Member member) {

        postService.updatePost(postId, updatePostRequest.toUpdatePostDto(), member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId,
                                           @CurrentMember Member member) {

        postService.deletePost(postId, member);
        return ResponseEntity.noContent().build();
    }
}