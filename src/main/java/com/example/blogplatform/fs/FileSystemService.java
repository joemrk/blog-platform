package com.example.blogplatform.fs;
import com.example.blogplatform.sftp.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileSystemService {
  private final SftpService sftpService;

  @Autowired
  public FileSystemService(SftpService sftpService) {
    this.sftpService = sftpService;
  }


  public String uploadBase64ToSftp(String base64) {
    ValidFile validatedFile = ImageValidator.getValidatedFile(base64);
    InputStream is = new ByteArrayInputStream(validatedFile.getBase64().getBytes());

    String filename = String.format(
            "blog_image_%s.%s",
            System.currentTimeMillis(),
            validatedFile.getExtension()
    );

    try {
      return sftpService.uploadFile(is, filename);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String uploadBase64ToLocalDir(String base64, String path) {
    // return path
    return "";
  }
}
