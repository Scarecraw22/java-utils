package io.github.scarecraw22.utils.docker.containers;

import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;
import java.util.Map;

public class SftpServerContainer extends BaseContainer {

    private static final String DOCKER_IMAGE = "lscr.io/linuxserver/openssh-server:latest";

    private static final String PUID = "PUID";
    private static final String PGID = "PGID";
    private static final String TIMEZONE = "TZ";
    private static final String PASSWORD_ACCESS = "PASSWORD_ACCESS";
    private static final String PASSWORD_ACCESS_VALUE = "true";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PASSWORD = "USER_PASSWORD";

    private final Map<String, String> envs;

    public SftpServerContainer() {
        this(getDefaultEnvs(), 2222);
    }

    public SftpServerContainer(Map<String, String> envs, int exposedPort) {
        super(new GenericContainer(DOCKER_IMAGE)
                        .withEnv(envs)
                        .withExposedPorts(exposedPort),
                "sftp-server"
        );
        this.envs = envs;
    }

    public String getUsername() {
        return envs.get(USER_NAME);
    }

    public String getPassword() {
        return envs.get(USER_PASSWORD);
    }

    private static Map<String, String> getDefaultEnvs() {
        Map<String, String> envs = new HashMap<>();
        envs.put(PUID, "1000");
        envs.put(PGID, "1000");
        envs.put(TIMEZONE, "Europe/Warsaw");
        envs.put(PASSWORD_ACCESS, PASSWORD_ACCESS_VALUE);
        envs.put(USER_NAME, "ssh-user");
        envs.put(USER_PASSWORD, "ssh-user-password");
        return envs;
    }
}
