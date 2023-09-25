package com.example.blogplatform.post.dto;

import com.example.blogplatform.tag.Tag;
import lombok.Data;

import java.util.List;

@Data
public class PostCreateDto {
  private String title;
  private String body;
  private ImageDto image;
  private Long categoryId;
  private List<Tag> tags;
}

