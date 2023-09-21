package com.example.blogplatform.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Category createOne(String newCategory) {
    Category category = Category.builder()
            .name(newCategory)
            .build();
    return categoryRepository.save(category);
  }

  public void deleteOne(Long id) {
    categoryRepository.deleteById(id);
  }
}
