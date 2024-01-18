package com.ll.project_13_backend.post.entity;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {

    KOR("kor"),
    ENG("eng"),
    MATH("math");

    Category(String value) {
        this.value = value;
    }

    public static Category match(String category) {
        return Arrays.stream(values())
                .filter(c -> c.value.equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다")); // 임시 코드
    }

    private final String value;
}
