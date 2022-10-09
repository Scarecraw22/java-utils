package io.github.scarecraw22.utils.port;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;

@Log4j2
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
