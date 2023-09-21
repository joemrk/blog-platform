package com.example.blogplatform.comment;

import com.example.blogplatform.post.Post;
import com.example.blogplatform.user.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Data
@Table(name ="comments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = CustomCommentSerializer.class)
public class Comment {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private String body;

  @CreationTimestamp
  private Date createdAt;

  @UpdateTimestamp
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "userId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;


  @ManyToOne
  @JoinColumn(name = "postId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Post post;
}
