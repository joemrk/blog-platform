package com.example.blogplatform.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByTitleLikeIgnoreCase(String title);

  Optional<Post> findByIdAndUserId(Long id, Long userId);

  List<Post> findByUserId(Long userId);
}
