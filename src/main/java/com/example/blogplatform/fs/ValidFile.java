package com.example.blogplatform.fs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidFile {
  private String extension;
  private String base64;
}
