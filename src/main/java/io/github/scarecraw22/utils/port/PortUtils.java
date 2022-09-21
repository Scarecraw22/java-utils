package io.github.scarecraw22.utils.port;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
@UtilityClass
public class PortUtils {

    public int getFreePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't find any free port", e);
        }
    }
}
