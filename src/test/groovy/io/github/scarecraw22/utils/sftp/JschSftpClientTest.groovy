package io.github.scarecraw22.utils.sftp

import com.jcraft.jsch.JSch
import io.github.scarecraw22.utils.file.FileUtils
import org.testcontainers.containers.GenericContainer
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
    private static GenericContainer CONTAINER = new GenericContainer("lscr.io/linuxserver/openssh-server:latest")
            .withEnv(ENVS)
            .withExposedPorts(2222)

    private SftpConfig sftpConfig = Mock()

    def setupSpec() {
        CONTAINER.start()
    }

    def cleanupSpec() {
        CONTAINER.stop()
    }

    def setup() {
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no")
        sftpConfig.getSshProperties() >> properties
        sftpConfig.getHost() >> CONTAINER.getHost()
        sftpConfig.getPort() >> CONTAINER.getFirstMappedPort()
        sftpConfig.getUsername() >> "test"
        sftpConfig.getPassword() >> "test"
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
