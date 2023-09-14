package com.example.blogplatform.post;

import com.example.blogplatform.exception.errors.NotFoundException;
import com.example.blogplatform.post.dto.PostCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post findById(Long id) {
    return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Post not found"));
  }

  public List<Post> findByTitle(String title) {
    return postRepository.findByTitleLikeIgnoreCase("%" + title + "%");
  }

  public Post createOne(PostCreateDto dto) {
    Post create = Post.builder()
            .body(dto.getBody())
            .title(dto.getTitle())
            .image(dto.getImage())
            .build();

    return postRepository.save(create);
  }

  public void delete(Long id) {
    postRepository.deleteById(id);
  }

  public Post update(Post post) {
    return postRepository.save(post);
  }
}
