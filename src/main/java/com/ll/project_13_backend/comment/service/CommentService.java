package com.ll.project_13_backend.comment.service;

import com.ll.project_13_backend.comment.entity.Comment;

public interface CommentService {
    Long createComment(final Comment comment);
    Comment findComment(final Long id);
    void modifiedComment(final Long id, Comment comment);
    void deleteComment(final Long id);
}
