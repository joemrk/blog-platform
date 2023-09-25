package com.example.blogplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogPlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogPlatformApplication.class, args);
  }


  // tags
  // fucking manytomany. did it itself

  // TODO: score like reddit - post_id, positive, negative
  // TODO: sftp service and options. check exist libs to make better
}
