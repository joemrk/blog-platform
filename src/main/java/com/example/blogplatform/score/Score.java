package com.example.blogplatform.score;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "score")
@AllArgsConstructor
@NoArgsConstructor
public class Score {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private byte score; // +1 or -1

  private Long postId;

  private Long userId;

}
