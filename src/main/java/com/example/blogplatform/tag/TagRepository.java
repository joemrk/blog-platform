package com.example.blogplatform.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

  List<Tag> findByNameLikeIgnoreCase(String name);

  List<Tag> findByIdIn(Set<Long> ids);
}
