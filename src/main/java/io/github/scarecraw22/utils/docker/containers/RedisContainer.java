package io.github.scarecraw22.utils.docker.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Path;

public class RedisContainer extends BaseContainer {

    private static final int DEFAULT_PORT = 6379;


    public RedisContainer(Path redisConfig) {
        this(redisConfig, DEFAULT_PORT);
    }

    public RedisContainer(Path redisConfig, int exposedPort) {
        super(new GenericContainer(DockerImageName.parse("redis:7.0.2-alpine"))
                .withCopyFileToContainer(MountableFile.forHostPath(redisConfig), "/usr/local/etc/redis/redis.conf")
                .withCommand("redis-server", "/usr/local/etc/redis/redis.conf")
                .withExposedPorts(exposedPort), "redis");
    }
}
