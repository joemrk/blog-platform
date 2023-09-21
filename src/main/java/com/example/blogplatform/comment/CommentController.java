package com.example.blogplatform.comment;

import com.example.blogplatform.annotations.CurrentUser;
import com.example.blogplatform.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }


  @GetMapping("/{id}")
  public ResponseEntity<Comment> findById(@PathVariable String id){
    return ResponseEntity.ok().body(this.commentService.findOne(Long.parseLong(id)));
  }

  @GetMapping("/")
  public ResponseEntity<List<Comment>> findAll(){
    return ResponseEntity.ok().body(this.commentService.findAll());
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<List<Comment>> findByPost(@PathVariable String id){
    return ResponseEntity.ok().body(this.commentService.findByPostId(Long.parseLong(id)));
  }

  @PostMapping
  public ResponseEntity<Comment> createOne(@RequestBody CommentCreateDto dto, @CurrentUser User current) {
    return ResponseEntity.ok().body(this.commentService.createOne(dto, current));
  }

  @PatchMapping
  public ResponseEntity<Comment> updateOne(@RequestBody Comment comment, @CurrentUser User current) {
    return ResponseEntity.ok().body(this.commentService.updateOne(comment, current));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Number> delete(@PathVariable("id") String id, @CurrentUser User current ){
    this.commentService.deleteOne(Long.parseLong(id), current);
    return ResponseEntity.ok(1);
  }
}
