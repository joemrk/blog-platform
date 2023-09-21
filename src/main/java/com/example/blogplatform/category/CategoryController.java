package com.example.blogplatform.category;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Category>> findAll() {
    return ResponseEntity.ok().body(this.categoryService.findAll());
  }

  @PostMapping
  public ResponseEntity<Category> createOne(@RequestBody CategoryCreateDto dto) {
    return ResponseEntity.ok().body(this.categoryService.createOne(dto.getName()));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Number> deleteOne(@PathParam("id") String id) {
    this.categoryService.deleteOne(Long.parseLong(id));
    return ResponseEntity.ok().body(1);
  }
}
