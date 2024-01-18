package com.ll.project_13_backend.post.repository;

import com.ll.project_13_backend.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
