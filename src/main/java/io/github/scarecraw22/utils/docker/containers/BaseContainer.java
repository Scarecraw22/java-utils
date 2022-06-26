package io.github.scarecraw22.utils.docker.containers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class BaseContainer {

    protected final GenericContainer container;
    protected final String name;

    public BaseContainer(GenericContainer container, String name) {
        this.container = container;
        this.name = name;
    }

    public synchronized void startWithStopOnShutdown() {
        if (!container.isRunning()) {
            container.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (container.isRunning()) {
                    log.info("Stopping {} container", name);
                    container.stop();
                    log.info("{} container stopped", name);
                }
            }));
        } else {
            log.info("{} container already running", name);
        }
    }

    public synchronized void stop() {
        if (container.isRunning()) {
            container.stop();
        }
    }

    public String getContainerHostAddress() {
        return container.getHost();
    }

    public Integer getFirstMappedPort() {
        return container.getFirstMappedPort();
    }
}
