package com.example.blogplatform.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

  private final TagRepository tagRepository;

  @Autowired
  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public Tag createOne(String name) {
    Tag tag = Tag.builder().name(name).build();
    return tagRepository.save(tag);
  }

  public List<Tag> findAll() {
    return tagRepository.findAll();
  }

  public List<Tag> findByName (String name) {
    return tagRepository.findByNameLikeIgnoreCase("%" + name + "%");
  }

  public Map<Long, Tag> findTagsIn(Set<Long> ids) {
    return tagRepository.findByIdIn(ids).stream()
            .collect(Collectors.toMap(Tag::getId, tag -> tag));
  }
}
