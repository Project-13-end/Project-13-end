package com.ll.project_13_backend.post.dto.response;

import com.ll.project_13_backend.post.dto.service.FindAllPostDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@Builder
public record FindAllPostResponse(Long postId,
                                  String name,
                                  String title,
                                  String content,
                                  String category,
                                  Long price,
                                  LocalDateTime createdDate) {

    public static FindAllPostResponse of(final FindAllPostDto findAllPostDto) {
        FindAllPostResponse findAllPostResponse = FindAllPostResponse.builder()
                .postId(findAllPostDto.getPostId())
                .name(findAllPostDto.getName())
                .title(findAllPostDto.getTitle())
                .content(findAllPostDto.getContent())
                .category(findAllPostDto.getCategory().getValue())
                .price(findAllPostDto.getPrice())
                .createdDate(findAllPostDto.getCreatedDate())
                .build();

        return findAllPostResponse;
    }

    public static Slice<FindAllPostResponse> of(final Slice<FindAllPostDto> findAllPostDtos) {
        List<FindAllPostResponse> findAllPostResponses = findAllPostDtos.getContent()
                .stream()
                .map(FindAllPostResponse::of)
                .toList();
        Slice<FindAllPostResponse> findAllPostResponseSlice =
                new SliceImpl<>(findAllPostResponses, findAllPostDtos.getPageable(), findAllPostDtos.hasNext());

        return findAllPostResponseSlice;
    }
}