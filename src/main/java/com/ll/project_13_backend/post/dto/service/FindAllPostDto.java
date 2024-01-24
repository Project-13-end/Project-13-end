package com.ll.project_13_backend.post.dto.service;

import com.ll.project_13_backend.post.entity.Category;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

//todo record로 queryprojection하는법알아보자
@Builder
@Getter
public class FindAllPostDto {
    private Long postId;
    private String name;
    private String title;
    private String content;
    private Category category;
    private Long price;
    private LocalDateTime createdDate;

    @QueryProjection
    public FindAllPostDto(Long postId,
                          String name,
                          String title,
                          String content,
                          Category category,
                          Long price,
                          LocalDateTime createdDate) {

        this.postId = postId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.category = category;
        this.price = price;
        this.createdDate = createdDate;
    }
}
