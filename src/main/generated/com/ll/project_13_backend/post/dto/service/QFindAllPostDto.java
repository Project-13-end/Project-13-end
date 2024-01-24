package com.ll.project_13_backend.post.dto.service;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ll.project_13_backend.post.dto.service.QFindAllPostDto is a Querydsl Projection type for FindAllPostDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFindAllPostDto extends ConstructorExpression<FindAllPostDto> {

    private static final long serialVersionUID = -281830083L;

    public QFindAllPostDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<com.ll.project_13_backend.post.entity.Category> category, com.querydsl.core.types.Expression<Long> price, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(FindAllPostDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, com.ll.project_13_backend.post.entity.Category.class, long.class, java.time.LocalDateTime.class}, postId, name, title, content, category, price, createdDate);
    }

}

