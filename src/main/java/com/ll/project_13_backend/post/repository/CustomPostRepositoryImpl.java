package com.ll.project_13_backend.post.repository;

import static com.ll.project_13_backend.post.entity.QPost.post;

import com.ll.project_13_backend.post.dto.service.FindAllPostDto;
import com.ll.project_13_backend.post.dto.service.QFindAllPostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class CustomPostRepositoryImpl implements CustomPostRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CustomPostRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public Slice<FindAllPostDto> findAllPost(final Pageable pageable) {
        List<FindAllPostDto> findAllPostDtos = queryFactory
                .select(new QFindAllPostDto(
                        post.id,
                        post.member.name,
                        post.title,
                        post.content,
                        post.category,
                        post.price,
                        post.createdDate))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (findAllPostDtos.size() > pageable.getPageSize()) {
            findAllPostDtos.remove(pageable.getPageSize());
            hasNext = true;
        }

        Slice<FindAllPostDto> result = new SliceImpl<>(findAllPostDtos, pageable, hasNext);
        return result;
    }
}