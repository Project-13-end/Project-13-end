package com.ll.project_13_backend.post.dto.request;

import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import lombok.Builder;

@Builder
public record UpdatePostRequest(String title, String content, String category, Long price) {

    public UpdatePostDto toUpdatePostDto() {
        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .title(title)
                .content(content)
                .category(Category.match(category))
                .price(price)
                .build();

        return updatePostDto;
    }
}
