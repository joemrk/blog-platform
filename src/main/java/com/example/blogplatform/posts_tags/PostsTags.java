package com.example.blogplatform.posts_tags;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "posts_tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsTags {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private Long postId;

  private Long tagId;

}
