package com.example.blogplatform.post;

import com.example.blogplatform.annotations.CurrentUser;
import com.example.blogplatform.post.dto.PostCreateDto;
import com.example.blogplatform.post.dto.PostResponse;
import com.example.blogplatform.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService service) {
    this.postService = service;
  }

  @GetMapping("/") // ?s={titleContains}
  public ResponseEntity<List<PostResponse>> findByTitle(@RequestParam("s") String s){
    return ResponseEntity.ok().body(postService.findByTitle(s));
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> findAll(){
    return ResponseEntity.ok().body(postService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> findById(@PathVariable String id){
    return ResponseEntity.ok().body(postService.findById(Long.parseLong(id)));
  }

  @GetMapping("/author/{id}")
  public ResponseEntity<List<PostResponse>> findByUser(@PathVariable String id){
    return ResponseEntity.ok().body(postService.findByUser(Long.parseLong(id)));
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<List<Post>> findByCategory(@PathVariable String id){
    return ResponseEntity.ok().body(postService.findByCategory(Long.parseLong(id)));
  }

  @GetMapping("/my")
  public ResponseEntity<List<PostResponse>> findByCurrentUser(@CurrentUser User current){
    return ResponseEntity.ok().body(postService.findByUser(current.getId()));
  }

  @PostMapping
  public ResponseEntity<Post> createOne(@RequestBody PostCreateDto dto, @CurrentUser User current ) {
    return ResponseEntity.ok().body(postService.createOne(dto, current));
  }

  @PatchMapping
  public ResponseEntity<Post> updateOne(@RequestBody Post post, @CurrentUser User current ){
    return ResponseEntity.ok().body(postService.update(post, current));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Number> delete(@PathVariable("id") String id, @CurrentUser User current ){
    this.postService.delete(Long.parseLong(id), current);
    return ResponseEntity.ok(1);
  }

}
