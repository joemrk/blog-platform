package com.example.blogplatform.post;

import com.example.blogplatform.exception.errors.BadRequestException;
import com.example.blogplatform.exception.errors.NotFoundException;
import com.example.blogplatform.fs.FileSystemService;
import com.example.blogplatform.post.dto.PostCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final FileSystemService fileSystemService;
  @Autowired
  public PostService(PostRepository postRepository, FileSystemService fileSystemService) {
    this.postRepository = postRepository;
    this.fileSystemService = fileSystemService;
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
    String imagePath = fileSystemService.uploadBase64ToSftp(dto.getImage().getBase64());
    System.out.println(imagePath);
    throw new BadRequestException("Bad request");
//    Post create = Post.builder()
//            .body(dto.getBody())
//            .title(dto.getTitle())
//            .image(imagePath)
//            .build();
//
//    return postRepository.save(create);
  }

  public void delete(Long id) {
    postRepository.deleteById(id);
  }

  public Post update(Post post) {
    return postRepository.save(post);
  }
}
