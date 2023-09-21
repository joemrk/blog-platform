package com.example.blogplatform.comment;

import lombok.Data;

@Data
public class CommentCreateDto {
  private String body;
  private Long postId;
}
