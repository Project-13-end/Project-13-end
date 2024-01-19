package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import java.security.Principal;
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
        Post post = createPostDto.createPostDtoToPost();
        Post id = postRepository.save(post);

        return id.getId();
    }

    private void checkLogin(Member member) {
        if (member == null) {
            throw new AuthException(ErrorCode.IS_NOT_LOGIN);
        }
    }
}
