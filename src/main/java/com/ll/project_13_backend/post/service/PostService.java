package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.entity.Post;

public interface PostService {

    Long createPost(final Post post, final Member member);
    Post findPost(final Long postId);
    void updatePost(final Long postId, final Post post, final Member member);
    void deletePost(final Long postId, final Member member);

}

