package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.dto.service.FindAllPostDto;
import com.ll.project_13_backend.post.dto.service.FindPostDto;
import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import org.springframework.data.domain.Slice;

public interface PostService {

    Long createPost(final CreatePostDto createPostDto, final Member member);

    FindPostDto findPost(final Long postId);

    void  updatePost(final Long postId, final UpdatePostDto updatePostDto, final Member member);

    void deletePost(final Long postId, final Member member);

    Slice<FindAllPostDto> findAllPost(final int page);

    Slice<FindAllPostDto> findAllPostByKeyword(final int page, final String keyword);
}
