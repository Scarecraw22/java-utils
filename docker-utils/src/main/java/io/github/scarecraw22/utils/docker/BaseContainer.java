package io.github.scarecraw22.utils.docker;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class BaseContainer {

    protected final GenericContainer container;
    protected final String name;

    /**
     * Creates Wrapper for {@link GenericContainer}.
     *
     * @param container {@link GenericContainer}.
     * @param name Container name.
     */
    public BaseContainer(GenericContainer container, String name) {
        this.container = container;
        this.name = name;
    }

    /**
     * Start container and schedules it's shutdown on JVM exit if it's still running.
     */
    public synchronized void startWithStopOnShutdown() {
        if (!container.isRunning()) {
            container.start();
            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        } else {
            log.info("{} container already running", name);
        }
    }

    /**
     * Stops container.
     */
    public synchronized void stop() {
        if (container.isRunning()) {
            log.info("Stopping {} container", name);
            container.stop();
            log.info("{} container stopped", name);
        }
    }

    public String getContainerHostAddress() {
        return container.getHost();
    }

    public Integer getFirstMappedPort() {
        return container.getFirstMappedPort();
    }
}
