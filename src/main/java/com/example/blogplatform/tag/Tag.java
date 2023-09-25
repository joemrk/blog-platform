package com.example.blogplatform.tag;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(
        name = "tags",
        indexes = {@Index(columnList = "name")}
)
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name="name", length=64, unique = true)
  private String name;

//  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
//  @JsonBackReference(value = "tags")
//  private List<Post> posts;
}
