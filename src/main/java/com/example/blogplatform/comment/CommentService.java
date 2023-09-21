package com.example.blogplatform.comment;

import com.example.blogplatform.exception.errors.NotFoundException;
import com.example.blogplatform.post.Post;
import com.example.blogplatform.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

  private final CommentRepository commentRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }


  public List<Comment> findAll() {
    return commentRepository.findAll();
  }


  public Comment findOne(Long id) {
    return commentRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Comment not found"));
  }

  public Comment createOne(CommentCreateDto dto, User current) {
    Comment comment = Comment.builder()
            .body(dto.getBody())
            .post(Post.builder().id(dto.getPostId()).build())
            .user(User.builder().id(current.getId()).build())
            .build();

    return commentRepository.save(comment);
  }

  public Comment updateOne(Comment comment, User current) {
    Comment exist = commentRepository.findByIdAndUserId(comment.getId(), current.getId());
    if (exist == null) {
      throw new NotFoundException("Comment not found");
    }
    exist.setBody(comment.getBody());

    return commentRepository.save(exist);
  }

  public void deleteOne(Long id, User current) {
    Comment exist = commentRepository.findByIdAndUserId(id, current.getId());
    if (exist != null) {
      commentRepository.deleteById(id);
    }
  }

  public List<Comment> findByPostId(Long postId) {
    return commentRepository.findByPostId(postId);
  }
}
