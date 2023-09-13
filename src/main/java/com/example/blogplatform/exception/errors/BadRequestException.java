package com.example.blogplatform.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.blogplatform.exception.CustomException;

@Getter
public class BadRequestException extends CustomException  {
  public BadRequestException(String message) {
    super(message, HttpStatus.BAD_REQUEST.value(), "Bad request");
  }
}
