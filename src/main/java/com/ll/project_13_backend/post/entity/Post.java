package com.ll.project_13_backend.post.entity;

import com.ll.project_13_backend.global.BaseEntity;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long price;


    public void updatePost(final UpdatePostDto updatePostDto) {
        this.title = updatePostDto.title();
        this.content = updatePostDto.content();
        this.category = updatePostDto.category();
        this.price = updatePostDto.price();
    }
}
