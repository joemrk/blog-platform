package com.example.blogplatform.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.blogplatform.exception.CustomException;

@Getter
public class ConflictException extends CustomException {
  public ConflictException(String message) {
    super(message, HttpStatus.CONFLICT.value(), "Conflict");
  }
}

