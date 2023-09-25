package com.example.blogplatform.tag;

import com.example.blogplatform.tag.dto.TagCreateDto;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping
  public ResponseEntity<Tag> createOne(@RequestBody TagCreateDto dto) {
    return ResponseEntity.ok().body(tagService.createOne(dto.getName()));
  }

  @GetMapping
  public ResponseEntity<List<Tag>> getAll(@Nullable @RequestParam String s) {
   if(s != null) {
     return ResponseEntity.ok().body(tagService.findByName(s));
   }

    return ResponseEntity.ok().body(tagService.findAll());
  }
}
