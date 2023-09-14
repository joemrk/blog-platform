package com.example.blogplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogPlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogPlatformApplication.class, args);
  }


  // TODO: comment - text, created_at, user_id, post_id
  // TODO: category - name
  // TODO: tags - name
  // TODO: score like reddit - post_id, positive, negative
}
