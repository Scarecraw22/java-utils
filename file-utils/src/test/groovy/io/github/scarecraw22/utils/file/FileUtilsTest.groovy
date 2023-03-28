package io.github.scarecraw22.utils.file

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileUtilsTest extends Specification {

    def "getFileFromResources() should return proper file"() {
        when:
        Path path = FileUtils.getFileFromResources("sample_file.txt")

        then:
        assert path.toString().contains("sample_file.txt")
    }

    def "getFileFromResources() should throw exception when file does not exist"() {
        when:
        FileUtils.getFileFromResources("file_that_doesnt_exist")

        then:
        thrown(FileNotFoundException)
    }

    def "readFileToString() should return file content"() {
        when:
        String content = FileUtils.readFileFromResourcesToString("sample_file.txt")

        then:
        content == "This is sample file :)"
    }

    def "readFileToString() should throw exception when file does not exist"() {
        when:
        FileUtils.readFileFromResourcesToString("file_that_doesnt_exist")

        then:
        thrown(FileNotFoundException)
    }

    def "getFileExtension() should retrieve proper file extension"() {
        when:
        Optional<String> extension = FileUtils.getExtension("sample_file.txt")

        then:
        extension.isPresent()
        extension.get().contains("txt")
    }

    def "getFileExtension() should properly return empty optional when there is no extension present in filename"() {
        when:
        Optional<String> extension = FileUtils.getExtension("sample_file")

        then:
        extension.isEmpty()
    }

    def "getTmpDir() should return proper tmp dir"() {
        when:
        Path path = FileUtils.getTmpDir()

        then:
        path.toFile().exists()
    }

    def "createTmpFile() should return newly created file"() {
        when:
        Path tmp = FileUtils.createTmpFile("sample", ".txt");

        then:
        tmp.toFile().exists()
        Optional<String> extension = FileUtils.getExtension(tmp.toString())
        extension.isPresent()
        extension.get().contains("txt")

        cleanup:
        FileUtils.deleteFile(tmp)
    }

    def "deleteNonEmptyDir() should remove all dirs with files within"() {
        given:
        Path resourcePath = FileUtils.getFileFromResources("sample_file.txt").getParent()
        Path newDir = Paths.get(resourcePath.toString())
                .resolve("tmp")
        Files.createDirectory(newDir)
        Path newFile = newDir.resolve("file_to_delete.txt")
        Files.createFile(newFile)
        Files.exists(newDir)
        Files.exists(newFile)

        when:
        FileUtils.deleteDir(newDir)

        then:
        !Files.exists(newDir)
        !Files.exists(newFile)
    }

    def "copyFile() should copy content to another file"() {
        given:
        Path src = FileUtils.getFileFromResources("sample_file.txt")
        Path dst = FileUtils.createTmpFile("copied", ".txt")

        when:
        FileUtils.copyFile(src, dst)

        then:
        FileUtils.readFileToString(dst) == FileUtils.readFileToString(src)

        cleanup:
        FileUtils.deleteFile(dst)
    }

    def "getBytesFromFile() should return proper byte array"() {
        when:
        Path src = FileUtils.getFileFromResources("sample_file.txt");

        then:
        FileUtils.toBytes(src).encodeHex().toString().toUpperCase() == "546869732069732073616D706C652066696C65203A29"
    }

    def 'writeBytesToFile() should write bytes to other file'() {
        given:
        Path destinationFile = FileUtils.createTmpFile("dummy", ".txt")
        byte[] bytes = FileUtils.toBytes(FileUtils.getFileFromResources("sample_file.txt"))

        when:
        FileUtils.writeBytesToFile(destinationFile, bytes)

        then:
        FileUtils.readFileToString(destinationFile) == "This is sample file :)"

        cleanup:
        FileUtils.deleteFile(destinationFile)
    }
}
