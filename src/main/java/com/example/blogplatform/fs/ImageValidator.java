package com.example.blogplatform.fs;

import com.example.blogplatform.exception.errors.BadRequestException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

public class ImageValidator {
  private static final Integer MAX_SIZE = 102400000;

  private static final List<String> imageExtensions = List.of("jpg", "jpeg", "png", "gif");

  private static final Pattern IMAGE_VALIDATE_REGEX = Pattern.compile(
          String.format("^(image)\\/(%s)$", String.join("|", imageExtensions))
  );

  private static final Pattern MIME_TYPE_VALIDATE_REGEX = Pattern.compile("[^:]\\w+/[\\w-+\\d.]+(?=[;,])", Pattern.MULTILINE);

  private static String getMimeType(String base64) {
    Matcher matcher = MIME_TYPE_VALIDATE_REGEX.matcher(base64);
    if (matcher.find()) {
      return matcher.group();
    }
    return null;
  }

  private static Integer getSize(String base64) {
//    return Base64.getDecoder().decode(base64).length;
    return base64.getBytes().length;
  }

  public static ValidFile getValidatedFile(String base64) {
    String mimeType = getMimeType(base64);
    if(mimeType == null || !IMAGE_VALIDATE_REGEX.matcher(mimeType).find()) {
      throw new BadRequestException("Invalid image format");
    }

    Integer size = getSize(base64);
    if (size > MAX_SIZE) {
      throw new BadRequestException("File is to large");
    }
    return ValidFile.builder()
            .base64(base64.replace("data:" + mimeType  + ";base64,", ""))
            .extension(mimeType.split("/" )[1])
            .build();
  }
}
