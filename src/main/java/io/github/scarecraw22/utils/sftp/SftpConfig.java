package io.github.scarecraw22.utils.sftp;

import java.util.Properties;

public interface SftpConfig {

    String getHost();

    Integer getPort();

    String getUsername();

    String getPassword();

    Properties getSshProperties();
}
