package io.github.scarecraw22.utils.file;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileUtilsTest {

    @Test
    public void getFileFromResourcesShouldReturnProperFileTest() {
        //when
        Path path = FileUtils.getFileFromResources("sample_file.txt");

        //then
        Assertions.assertThat(path.toString()).contains("sample_file.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void getFileFromResourcesShouldThrowExceptionWhenFileDoesNotExistTest() {
        //when & then
        FileUtils.getFileFromResources("file_that_doesnt_exist");
    }

    @Test
    public void readFileToStringShouldReturnFileContentTest() {
        //when
        String content = FileUtils.readFileFromResourcesToString("sample_file.txt");

        //then
        Assertions.assertThat(content).isEqualTo("This is sample file :)");
    }

    @Test(expected = FileNotFoundException.class)
    public void readFileToStringShouldThrowExceptionWhenFileDoesNotExistTest() {
        //when & then
        FileUtils.readFileFromResourcesToString("file_that_doesnt_exist");
    }

    @Test
    public void getFileExtensionShouldProperlyRetrieveFileExtensionTest() {
        // when
        Optional<String> extension = FileUtils.getExtension("sample_file.txt");

        // then
        Assertions.assertThat(extension)
                .isPresent()
                .contains("txt");
    }

    @Test
    public void getFileExtensionShouldProperlyRetrieveFileExtensionWhenThereIsNoExtensionTest() {
        // when
        Optional<String> extension = FileUtils.getExtension("sample_file");

        // then
        Assertions.assertThat(extension)
                .isEmpty();
    }

    @Test
    public void getTmpDirTest() {
        // when && then
        Assertions.assertThat(FileUtils.getTmpDir())
                .exists();
    }

    @Test
    public void createTmpFileShouldReturnNewlyCreatedFile() {
        // when
        Path tmp = FileUtils.createTmpFile("sample", ".txt");

        // then
        Assertions.assertThat(tmp.toFile()).exists();
        Assertions.assertThat(FileUtils.getExtension(tmp.toString()))
                .isPresent()
                .contains("txt");

        // cleanup
        FileUtils.deleteFile(tmp);
    }

    @Test
    public void deleteNonEmptyDirShouldRemoveAllDirsWithFilesWithinTest() throws IOException {
        // given
        Path resourcePath = FileUtils.getFileFromResources("sample_file.txt").getParent();
        Path newDir = Paths.get(resourcePath.toString())
                .resolve("tmp");
        Files.createDirectory(newDir);
        Path newFile = newDir.resolve("file_to_delete.txt");
        Files.createFile(newFile);
        Assertions.assertThat(Files.exists(newDir)).isTrue();
        Assertions.assertThat(Files.exists(newFile)).isTrue();

        // when
        FileUtils.deleteDir(newDir);

        // then
        Assertions.assertThat(Files.exists(newDir)).isFalse();
        Assertions.assertThat(Files.exists(newFile)).isFalse();
    }

    @Test
    public void copyFileShouldCopyContentToAnotherFile() {
        // given
        Path src = FileUtils.getFileFromResources("sample_file.txt");
        Path dst = FileUtils.createTmpFile("copied", ".txt");

        // when
        FileUtils.copyFile(src, dst);

        // then
        Assertions.assertThat(FileUtils.readFileToString(dst))
                .isEqualTo(FileUtils.readFileToString(src));

        // cleanup
        FileUtils.deleteFile(dst);
    }

    @Test
    public void getBytesFromFile() {
        // when
        Path src = FileUtils.getFileFromResources("sample_file.txt");

        // then
        Assertions.assertThat(FileUtils.toBytes(src)).asHexString().isEqualTo("546869732069732073616D706C652066696C65203A29");
    }
}
