package com.example.blogplatform.post;

import com.example.blogplatform.post.dto.PostResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomPostSerializer extends JsonSerializer<PostResponse> {
  @Override
  public void serialize(PostResponse post, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();

    gen.writeNumberField("id", post.getId());
    gen.writeStringField("title", post.getTitle());
    gen.writeStringField("body", post.getBody());
    gen.writeStringField("image", post.getImage());
    gen.writeStringField("createdAt", post.getCreatedAt().toString());
    gen.writeStringField("updatedAt", post.getUpdatedAt().toString());

    if(post.getUser() == null){
      gen.writeNullField("user");
    } else {
      gen.writeFieldName("user");
      gen.writeStartObject();
      gen.writeNumberField("id", post.getUser().getId());
      gen.writeEndObject();
    }

    if(post.getCategory() == null){
      gen.writeNullField("category");
    } else {
      gen.writeFieldName("category");
      gen.writeStartObject();
      gen.writeNumberField("id", post.getCategory().getId());
      gen.writeStringField("name", post.getCategory().getName());
      gen.writeEndObject();
    }

    gen.writeFieldName("tags");
    gen.writeStartArray();

    post.getTags().forEach(tag -> {
      try {
        gen.writeStartObject();
        gen.writeNumberField("id", tag.getId());
        gen.writeStringField("name", tag.getName());
        gen.writeEndObject();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    gen.writeEndArray();

    gen.writeEndObject();
  }
}
