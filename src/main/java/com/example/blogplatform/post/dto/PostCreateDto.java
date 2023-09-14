package com.example.blogplatform.post.dto;

import lombok.Data;

@Data
public class PostCreateDto {
  private String title;
  private String body;
  private String image;
}
