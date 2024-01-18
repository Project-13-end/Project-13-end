package com.ll.project_13_backend.comment.repository;

import com.ll.project_13_backend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
