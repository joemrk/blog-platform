package com.example.blogplatform.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogplatform.user.User;

@RestController
@RequestMapping("/score")
public class ScoreController {
  
  private final ScoreService scoreService;

  @Autowired
  public ScoreController(ScoreService scoreService) {
    this.scoreService = scoreService;
  }


  @PostMapping("/post-up/{postId}")
  public ResponseEntity<Number> scoreUp(
    @PathVariable("postId") String postId,
    @AuthenticationPrincipal User user) {

    this.scoreService.scoreUp(Long.valueOf(postId), user.getId());
    return ResponseEntity.ok().body(1);
  }

  @PostMapping("/post-down/{postId}")
  public ResponseEntity<Number> scoreDown(
    @PathVariable("postId") String postId,
    @AuthenticationPrincipal User user) {

    this.scoreService.scoreDown(Long.valueOf(postId), user.getId());
    return ResponseEntity.ok().body(1);
  }

}
