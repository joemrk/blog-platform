package com.example.blogplatform.score.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostScores {
  private Long post_id;
  private Integer score;
}
