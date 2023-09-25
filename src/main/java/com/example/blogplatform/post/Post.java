package com.example.blogplatform.post;

import com.example.blogplatform.category.Category;
import com.example.blogplatform.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name="posts")
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Post {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name="title")
  private String title;

  @Column(name="body", length=512)
  private String body;

  private String image;

  @CreationTimestamp
  private Date createdAt;

  @UpdateTimestamp
  private Date updatedAt;

  @ManyToOne
  @JoinColumn(name = "userId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne
  @JoinColumn(name = "categoryId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Category category;

//  @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY)
//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(
//          name = "posts_tags",
//          joinColumns= @JoinColumn(name = "postId", referencedColumnName = "id"),
//          inverseJoinColumns = @JoinColumn(name = "tagId", referencedColumnName = "id"))
//  @JsonManagedReference(value = "tags")
//  private List<Tag> tags;
}
