package com.example.blogplatform.post;

import com.example.blogplatform.post.dto.PostCreateDto;
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
  public ResponseEntity<List<Post>> findByTitle(@RequestParam("s") String s){
    return ResponseEntity.ok().body(postService.findByTitle(s));
  }

  @GetMapping
  public ResponseEntity<List<Post>> findAll(){
    return ResponseEntity.ok().body(postService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> findById(@PathVariable String id){
    return ResponseEntity.ok().body(postService.findById(Long.parseLong(id)));
  }

  @PostMapping
  public ResponseEntity<Post> createOne(@RequestBody PostCreateDto dto) {
    return ResponseEntity.ok().body(postService.createOne(dto));
  }

  @PatchMapping
  public ResponseEntity<Post> updateOne(@RequestBody Post post){
    return ResponseEntity.ok().body(postService.update(post));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Number> delete(@PathVariable("id") String id){
    this.postService.delete(Long.parseLong(id));
    return ResponseEntity.ok(1);
  }

}
