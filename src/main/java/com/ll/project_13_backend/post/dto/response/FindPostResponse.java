package com.ll.project_13_backend.post.dto.response;


import com.ll.project_13_backend.post.dto.service.FindPostDto;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FindPostResponse(String name,
                               String title,
                               String content,
                               String category,
                               Long price,
                               LocalDateTime createdDate) {

    //todo findPostDto에서 플랫폼 확장으로 고려해야 할 때 to로 변환을 받아야 하는가 아니면 of로 변환을 받아야 하는가 생각해 보자
    public static FindPostResponse of(FindPostDto findPostDto) {
        FindPostResponse findPostResponse = FindPostResponse.builder()
                .name(findPostDto.name())
                .title(findPostDto.title())
                .content(findPostDto.content())
                .category(findPostDto.category().getValue())
                .price(findPostDto.price())
                .createdDate(findPostDto.createdDate())
                .build();

        return findPostResponse;
    }
}
