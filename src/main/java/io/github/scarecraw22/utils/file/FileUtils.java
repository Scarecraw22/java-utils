package io.github.scarecraw22.utils.file;

import io.github.scarecraw22.utils.string.StringConsts;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@UtilityClass
public class FileUtils {

    public Path getFileFromResources(@NonNull String pathString) {
        try {
            URL url = FileUtils.class.getClassLoader().getResource(pathString);
            if (url == null) {
                throw new FileNotFoundException("File: " + pathString + " not found");
            }
            return Paths.get(url.toURI());

        } catch (URISyntaxException e) {
            throw new FileUtilsException(e);
        }
    }

    public String readFileFromResourcesToString(@NonNull String pathString) {
        return readFileToString(getFileFromResources(pathString));
    }

    public String readFileToString(@NonNull Path path) {
        try {
            return String.join(StringConsts.NEW_LINE, Files.readAllLines(path));

        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }

    public void copyFile(@NonNull Path src, @NonNull Path dst) {
        try {
            if (!dst.toFile().exists()) {
                Files.createFile(dst);
            } else {
                log.warn("Destination file: {} already exists and it will be replaced!", dst);
            }
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }

    public Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains(StringConsts.DOT))
                .map(f -> f.substring(filename.lastIndexOf(StringConsts.DOT) + 1));
    }

    public byte[] toBytes(@NonNull Path file) {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }

    public byte[] toBytes(@NonNull File file) {
        return toBytes(file.toPath());
    }

    public void deleteDir(@NonNull File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                Arrays.asList(files)
                        .forEach(FileUtils::deleteDir);
            }

            file.delete();

        } else if (file.isFile()) {
            deleteFile(file);
        } else {
            throw new FileUtilsException("Given object is not a file and not a directory: " + file);
        }
    }

    public void deleteDir(@NonNull Path path) {
        deleteDir(path.toFile());
    }

    public void createDirs(@NonNull File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void createDirs(@NonNull Path path) {
        createDirs(path.toFile());
    }

    public Path createTmpFile(@NonNull String prefix, @NonNull String suffix) {
        try {
            return Files.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }

    public Path getTmpDir() {
        return Paths.get(System.getProperty("java.io.tmpdir"));
    }

    public void deleteFile(@NonNull Path path) {
        if (path.toFile().isDirectory()) {
            throw new FileUtilsException("Given path is a directory. Use deleteDir() method to delete directory.");
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }

    public void deleteFile(@NonNull File file) {
        deleteFile(file.toPath());
    }

    public void writeInputStreamToFile(InputStream inputStream, Path target) {
        try {
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new FileUtilsException(e);
        }
    }
}
