package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.dto.service.FindAllPostDto;
import com.ll.project_13_backend.post.dto.service.FindPostDto;
import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Slice<FindAllPostDto> findAllPost(final int page) {
        final List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        final Pageable pageable = PageRequest.of(page, 20,Sort.by(sorts));
        final Slice<FindAllPostDto> slice = postRepository.findAllPost(pageable);
        return slice;
    }
//todo 중복된 코드에 대해 생각해보자
    public Slice<FindAllPostDto> findAllPostByKeyword(final int page, final String keyword) {
        final List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        final Pageable pageable = PageRequest.of(page, 20,Sort.by(sorts));
        final Slice<FindAllPostDto> slice = postRepository.findAllPostByKeyword(pageable, keyword);
        return slice;
    }

    @Transactional
    public Long createPost(final CreatePostDto createPostDto, final Member member) {
        checkLogin(member);
        //todo member객체를 넣어도 괜찮나 더 좋은 방법이 없나에 대해 생각
        final Post post = createPostDto.toPost(member);
        final Post id = postRepository.save(post);

        return id.getId();
    }

    public FindPostDto findPost(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final FindPostDto findPostDto = FindPostDto.of(post);
        return findPostDto;
    }

    @Transactional
    public void updatePost(final Long postId, final UpdatePostDto updatePostDto, final Member member) {
        checkAnonymous(member);
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        post.checkAuthorized(member);
        post.updatePost(updatePostDto);
    }

    @Transactional
    public void deletePost(final Long postId, final Member member) {
        checkAnonymous(member);
//        if (!postRepository.existsById(postId)) { //todo 원하지 않는 예외가 발생함 원인을 모름 물어보자
//            throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);
//        }
//        postRepository.deleteById(postId);
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        post.checkAuthorized(member);
        postRepository.delete(post);
    }

    private static void checkAnonymous(final Member member) {
        if (member == null) {
            throw new AuthException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    private void checkLogin(final Member member) {
        if (member == null) {
            throw new AuthException(ErrorCode.IS_NOT_LOGIN);
        }
    }
}
