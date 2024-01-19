package com.ll.project_13_backend.post.dto.service;

import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import lombok.Builder;

@Builder
public record CreatePostDto(String title, String content, Category category, Long price) {

    public Post createPostDtoToPost() {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .price(price)
                .build();
        return post;
    }
}
