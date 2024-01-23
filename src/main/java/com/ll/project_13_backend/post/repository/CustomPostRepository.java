package com.ll.project_13_backend.post.repository;

import com.ll.project_13_backend.post.dto.service.FindAllPostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomPostRepository {
    Slice<FindAllPostDto> findAllPost(final Pageable pageable);
    Slice<FindAllPostDto> findAllPostByKeyword(final Pageable pageable, final String keyword);
}
