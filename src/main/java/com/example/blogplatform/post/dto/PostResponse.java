package com.example.blogplatform.post.dto;

import com.example.blogplatform.category.Category;
import com.example.blogplatform.post.CustomPostSerializer;
import com.example.blogplatform.tag.Tag;
import com.example.blogplatform.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = CustomPostSerializer.class)
public class PostResponse {
  private Long id;

  private String title;

  private String body;

  private String image;

  private Date createdAt;

  private Date updatedAt;

  private User user;

  private Category category;

  private List<Tag> tags;
}
