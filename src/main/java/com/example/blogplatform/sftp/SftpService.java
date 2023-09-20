package com.example.blogplatform.sftp;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

@Service
public class SftpService {
  // Spring doesn’t support @Value on static fields.
  @Value("${sftp.host}")
  private String host;
  @Value("${sftp.port}")
  private String port;
  @Value("${sftp.user}")
  private String user;
  @Value("${sftp.password}")
  private String password;
  @Value("${sftp.destination}")
  private String destinationPath;

  private Session session = null;
  private ChannelSftp channelSftp = null;

  public String uploadFile(
          InputStream inputStream,
          String filename
  ) throws Exception {
    try {
      JSch jsch = new JSch();
      session = jsch.getSession(this.user, this.host, Integer.valueOf(this.port));
      session.setPassword(this.password);

      Properties config = new Properties();
      config.put("StrictHostKeyChecking", "no");

      session.setConfig(config);
      session.connect();
      channelSftp = (ChannelSftp) session.openChannel("sftp");
      channelSftp.connect();
      channelSftp.cd(destinationPath);
      channelSftp.put(inputStream, filename, ChannelSftp.OVERWRITE);

      return String.format("%s/%s", destinationPath, filename);
    } catch (Exception e) {
      throw e;
    } finally {
      this.close();
    }
  }

  public void close() {
    if (channelSftp != null && channelSftp.isConnected()) {
      channelSftp.disconnect();
    }

    if (session != null && session.isConnected()) {
      session.disconnect();
    }
  }
}
