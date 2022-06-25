package io.github.scarecraw22.utils.sftp;

import com.jcraft.jsch.*;
import io.github.scarecraw22.utils.file.FileUtils;
import io.github.scarecraw22.utils.file.FileUtilsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class JschSftpClient implements SftpClient {

    private final JSch jsch;
    private final SftpConfig sftpConfig;

    @Override
    public void upload(InputStream inputStream, String destinationPath) throws UploadFileSftpException {
        tryOrThrowUploadException(channelSftp -> {
            tryUploadOrThrowUploadException(channelSftp, inputStream, destinationPath);
        });
    }

    @Override
    public void upload(Path sourceFile, String destinationPath) throws UploadFileSftpException {
        tryOrThrowUploadException(channelSftp -> {
            try (InputStream inputStream = Files.newInputStream(sourceFile)) {
                tryUploadOrThrowUploadException(channelSftp, inputStream, destinationPath);

            } catch (IOException e) {
                log.warn("Error while creating InputStream from file: {}", sourceFile);
                throw new UploadFileSftpException(e);
            }
        });
    }

    @Override
    public void download(String sourcePath, Path destinationFile) throws DownloadFileSftpException {
        tryOrThrowDownloadException(channelSftp -> {
            try {
                FileUtils.writeInputStreamToFile(tryDownloadOrThrowDownloadException(channelSftp, sourcePath), destinationFile);

            } catch (FileUtilsException e) {
                log.warn("Error while trying to copy InputStream to file: {}", destinationFile);
                throw new DownloadFileSftpException(e);
            }
        });
    }

    @Override
    public void download(String sourcePath, Consumer<InputStream> onDownloadConsumer) throws DownloadFileSftpException {
        tryOrThrowDownloadException(channelSftp -> {
            try (InputStream inputStream = tryDownloadOrThrowDownloadException(channelSftp, sourcePath)) {
                onDownloadConsumer.accept(inputStream);

            } catch (IOException e) {
                log.warn("Error while closing InputStream");
                throw new DownloadFileSftpException(e);
            }
        });
    }

    private InputStream tryDownloadOrThrowDownloadException(ChannelSftp channelSftp,
                                                            String sourcePath) throws DownloadFileSftpException {
        try {
            log.debug("Trying to download file: {}", sourcePath);
            return channelSftp.get(sourcePath);
        } catch (com.jcraft.jsch.SftpException e) {
            log.warn("Error while downloading file from: {}", sourcePath);
            throw new DownloadFileSftpException(e);
        }
    }

    private void tryUploadOrThrowUploadException(ChannelSftp channelSftp,
                                                 InputStream inputStream,
                                                 String destinationPath) throws UploadFileSftpException {
        try {
            log.debug("Trying to upload file to: {}", destinationPath);
            channelSftp.put(inputStream, destinationPath);
        } catch (com.jcraft.jsch.SftpException e) {
            log.warn("Error while uploading file to: {}", destinationPath);
            throw new UploadFileSftpException(e);
        }
    }

    private void tryOrThrowUploadException(ChannelSftpConsumer consumer) throws UploadFileSftpException {
        try {
            doOnConnected(consumer);
        } catch (SftpException e) {
            throw new UploadFileSftpException(e);
        }
    }

    private void tryOrThrowDownloadException(ChannelSftpConsumer consumer) throws DownloadFileSftpException {
        try {
            doOnConnected(consumer);
        } catch (SftpException e) {
            throw new DownloadFileSftpException(e);
        }
    }

    private void doOnConnected(ChannelSftpConsumer consumer) throws SftpException {
        String host = sftpConfig.getHost();
        Integer port = sftpConfig.getPort();

        Session session = null;
        ChannelSftp channelSftp = null;

        log.debug("Trying to connect to: {}:{}", host, port);
        try {
            session = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHost(), sftpConfig.getPort());
            session.setPassword(sftpConfig.getPassword().getBytes(StandardCharsets.UTF_8));

            if (sftpConfig.getSshProperties() != null) {
                session.setConfig(sftpConfig.getSshProperties());

            }

            session.connect();
            log.debug("Connected !");

            log.debug("Trying to open SFTP channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.debug("Opened !");

            consumer.accept(channelSftp);
        } catch (JSchException e) {
            log.warn("Error while connecting to: {}:{}", host, port);
            throw new SftpException(e);
        } finally {
            if (channelSftp != null) {
                log.debug("Trying to close SFTP channel");
                channelSftp.disconnect();
            }
            if (session != null) {
                log.debug("Trying to close session");
                session.disconnect();
            }
        }
    }

    private interface ChannelSftpConsumer {
        void accept(ChannelSftp channelSftp) throws SftpException;
    }
}
