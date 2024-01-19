package com.ll.project_13_backend.post.dto.request;

import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import lombok.Builder;

@Builder
public record CreatePostRequest(String title, String content, String category, Long price) {

    public CreatePostDto createPostRequestTocreatePostDto() {
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title(title)
                .content(content)
                .category(Category.match(category))
                .price(price)
                .build();

        return createPostDto;
    }
}
