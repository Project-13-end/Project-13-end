package com.ll.project_13_backend.post.dto.service;

import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FindPostDto(String name,
                          String title,
                          String content,
                          Category category,
                          Long price,
                          LocalDateTime createdDate) {

    public static FindPostDto of(Post post) {
        FindPostDto findPostDto = FindPostDto.builder()
                .name(post.getMember().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .price(post.getPrice())
                .createdDate(post.getCreatedDate())
                .build();

        return findPostDto;
    }
}