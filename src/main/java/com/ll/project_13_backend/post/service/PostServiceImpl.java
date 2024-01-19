package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.dto.service.FindPostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long createPost(final CreatePostDto createPostDto, final Member member) {
        checkLogin(member);
        //todo member객체를 넣어도 괜찮나 더 좋은 방법이 없나에 대해 생각
        Post post = createPostDto.createPostDtoToPost(member);
        Post id = postRepository.save(post);

        return id.getId();
    }

    public FindPostDto findPost(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        FindPostDto findPostDto = FindPostDto.of(post);
        return findPostDto;
    }

    private void checkLogin(Member member) {
        if (member == null) {
            throw new AuthException(ErrorCode.IS_NOT_LOGIN);
        }
    }
}
