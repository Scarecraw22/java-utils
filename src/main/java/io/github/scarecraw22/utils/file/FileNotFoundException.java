package io.github.scarecraw22.utils.file;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
