package com.ll.project_13_backend.post.dto.request;

import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdatePostRequest(@NotBlank(message = "제목을 반드시 입력해주세요.") String title,
                                @NotBlank(message = "내용을 반드시 입력해주세요.") String content,
                                @NotBlank(message = "카테고리를 반드시 선택해주세요.") String category,
                                @NotNull(message = "가격을 반드시 입력해주세요.") Long price) {

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
