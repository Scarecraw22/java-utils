package io.github.scarecraw22.utils.docker;

import lombok.NonNull;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

public class RedisContainer extends BaseContainer {

    private static final int DEFAULT_PORT = 6379;
    private static final String DEFAULT_IMAGE = "redis:7.2.0-alpine";

    public RedisContainer(Config config) {
        super(new GenericContainer(DockerImageName.parse(getImageOrDefault(config)))
                .withCopyFileToContainer(getRedisConfigOrDefault(config), "/usr/local/etc/redis/redis.conf")
                .withCommand("redis-server", "/usr/local/etc/redis/redis.conf")
                .withExposedPorts(getPortOrDefault(config)), "redis");
    }

    private static MountableFile getRedisConfigOrDefault(Config config) {
        return config.redisConfigPath() != null
                ? MountableFile.forHostPath(config.redisConfigPath())
                : MountableFile.forHostPath("redis/redis.conf");
    }

    private static int getPortOrDefault(Config config) {
        return config.port() != null
                ? config.port()
                : DEFAULT_PORT;
    }

    private static String getImageOrDefault(Config config) {
        return config.image() != null
                ? config.image()
                : DEFAULT_IMAGE;
    }

    public record Config(String image,
                         String redisConfigPath,
                         Integer port) {
    }
}
