package io.github.scarecraw22.utils.sftp;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Consumer;

public interface SftpClient {

    void upload(InputStream inputStream, String destinationPath) throws UploadFileSftpException;

    void upload(Path sourceFile, String destinationPath) throws UploadFileSftpException;

    void download(String sourcePath, Path destinationFile) throws DownloadFileSftpException;

    void download(String sourcePath, Consumer<InputStream> onDownloadConsumer) throws DownloadFileSftpException;
}
