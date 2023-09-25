package com.example.blogplatform.tag.dto;

import com.example.blogplatform.tag.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class TagCreateDto {
  private String name;
  private Set<Tag> tags;
}
