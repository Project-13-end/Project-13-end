package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.entity.Post;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    public Long createPost(final Post post, final Member member){return null;}
    public Post findPost(final Long postId){return null;}
    public void updatePost(final Long postId, final Post post, final Member member){}
    public void deletePost(final Long postId, final Member member){}
}