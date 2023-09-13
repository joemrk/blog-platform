package com.example.blogplatform.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.blogplatform.exception.CustomException;

@Getter
public class NotFoundException extends CustomException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND.value(), "Not found");
  }
}
