package com.example.blogplatform.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostId(long postId);

  Comment findByIdAndUserId(long id, Long userId);
}
