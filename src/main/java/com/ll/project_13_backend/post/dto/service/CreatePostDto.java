package com.ll.project_13_backend.post.dto.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import lombok.Builder;

@Builder
public record CreatePostDto(String title, String content, Category category, Long price) {

    public Post toPost(Member member) {
        Post post = Post.builder()
                .member(member)
                .title(title)
                .content(content)
                .category(category)
                .price(price)
                .build();
        return post;
    }
}
