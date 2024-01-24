package com.ll.project_13_backend.comment.service;

import com.ll.project_13_backend.comment.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    public Long createComment(final Comment comment){return null;}
    public Comment findComment(final Long id){return null;}
    public void modifiedComment(final Long id, Comment comment){}
    public void deleteComment(final Long id){}
}
