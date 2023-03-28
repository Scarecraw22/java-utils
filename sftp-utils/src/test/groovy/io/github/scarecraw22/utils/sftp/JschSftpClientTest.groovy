package io.github.scarecraw22.utils.sftp

import com.jcraft.jsch.JSch
import io.github.scarecraw22.utils.docker.SftpServerContainer
import io.github.scarecraw22.utils.file.FileUtils
import spock.lang.Specification

import java.nio.file.Path

class JschSftpClientTest extends Specification {

    private static final Map<String, String> ENVS = [
            "PUID"           : "1000",
            "PGID"           : "1000",
            "TZ"             : "Europe/Warsaw",
            "PASSWORD_ACCESS": "true",
            "USER_NAME"      : "test",
            "USER_PASSWORD"  : "test",
    ]

    private static final SftpServerContainer SFTP_CONTAINER = new SftpServerContainer(ENVS, 2222)

    private SftpConfig sftpConfig = Mock()

    def setupSpec() {
        SFTP_CONTAINER.startWithStopOnShutdown()
    }

    def cleanupSpec() {
        SFTP_CONTAINER.stop()
    }

    def setup() {
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no")
        sftpConfig.getSshProperties() >> properties
        sftpConfig.getHost() >> SFTP_CONTAINER.getContainerHostAddress()
        sftpConfig.getPort() >> SFTP_CONTAINER.getFirstMappedPort()
        sftpConfig.getUsername() >> SFTP_CONTAINER.getUsername()
        sftpConfig.getPassword() >> SFTP_CONTAINER.getPassword()
    }

    def "JschSftpClient flow test"() {
        given:
        SftpClient sftpClient = new JschSftpClient(new JSch(), sftpConfig)
        Path transferredFile = FileUtils.getFileFromResources("sftp/sample_file.txt")

        when:
        sftpClient.upload(transferredFile, "./sample_file.txt")

        then:
        noExceptionThrown()

        when:
        Path fileFromSftp = FileUtils.createTmpFile("file_from_sftp", ".txt")
        sftpClient.download("./sample_file.txt", fileFromSftp)

        then:
        noExceptionThrown()
        fileFromSftp.getText() == "Content of transferred file"
    }
}
