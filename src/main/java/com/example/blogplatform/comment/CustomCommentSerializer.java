package com.example.blogplatform.comment;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomCommentSerializer extends JsonSerializer<Comment> {
  @Override
  public void serialize(Comment comment, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    gen.writeNumberField("id", comment.getId());
    gen.writeStringField("body", comment.getBody());
    gen.writeStringField("createdAt", comment.getCreatedAt().toString());
    gen.writeStringField("updatedAt", comment.getUpdatedAt().toString());

    if(comment.getPost() == null) {
      gen.writeNullField("post");
    } else {
      gen.writeFieldName("post");
      gen.writeStartObject();
      gen.writeNumberField("id", comment.getPost().getId());
      gen.writeEndObject();
    }

    if(comment.getUser() == null){
      gen.writeNullField("user");
    } else {
      gen.writeFieldName("user");
      gen.writeStartObject();
      gen.writeNumberField("id", comment.getUser().getId());
      gen.writeEndObject();
    }

    gen.writeEndObject();
  }
}
