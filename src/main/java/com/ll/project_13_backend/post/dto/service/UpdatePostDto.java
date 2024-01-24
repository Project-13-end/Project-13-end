package com.ll.project_13_backend.post.dto.service;

import com.ll.project_13_backend.post.entity.Category;
import lombok.Builder;

@Builder
public record UpdatePostDto(String title, String content, Category category, Long price) {

}
