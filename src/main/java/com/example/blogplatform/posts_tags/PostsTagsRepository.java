package com.example.blogplatform.posts_tags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostsTagsRepository extends JpaRepository<PostsTags, Long> {

  List<PostsTags> findByPostIdIn(Set<Long> postIds);
}
