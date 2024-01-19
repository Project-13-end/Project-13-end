package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;

public interface PostService {

    Long createPost(final CreatePostDto createPostDto, final Member member);
}
