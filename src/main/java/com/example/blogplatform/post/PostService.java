package com.example.blogplatform.post;

import com.example.blogplatform.category.Category;
import com.example.blogplatform.exception.errors.NotFoundException;
import com.example.blogplatform.fs.FileSystemService;
import com.example.blogplatform.post.dto.PostCreateDto;
import com.example.blogplatform.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  public Post createOne(PostCreateDto dto, User current) {
    String imagePath = fileSystemService.uploadBase64ToSftp(dto.getImage().getBase64());
    Post create = Post.builder()
            .body(dto.getBody())
            .title(dto.getTitle())
            .user(User.builder().id(current.getId()).build())
            .category(Category.builder().id(dto.getCategoryId()).build())
            .image(imagePath)
            .build();

    return postRepository.save(create);
  }

  public void delete(Long id, User current) {
    Post exist = postRepository.findByIdAndUserId(id, current.getId());
    if(exist != null){
      postRepository.deleteById(id);
    }
  }

  public Post update(Post post, User current) {
    Post exist = postRepository.findByIdAndUserId(post.getId(), current.getId());
    if(exist == null){
      throw new NotFoundException("Post not found");
    }
    exist.setBody(post.getBody());
    exist.setTitle(post.getTitle());

    return postRepository.save(exist);
  }

  public List<Post> findByUser(Long userId) {
    return postRepository.findByUserId(userId);
  }

  public List<Post> findByCategory(Long userId) {
    return postRepository.findByCategoryId(userId);
  }
}
