package io.github.scarecraw22.utils.docker;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlContainer extends BaseContainer {

    private static final String DEFAULT_IMAGE = "postgres:15.2-alpine";

    public PostgresqlContainer(Config config) {
        super(new PostgreSQLContainer(config.image() != null
                        ? config.image()
                        : DEFAULT_IMAGE)
                        .withDatabaseName(config.databaseName())
                        .withUsername(config.username())
                        .withPassword(config.password())
                        .withInitScript(config.initScriptPath()),
                "Postgres");
    }

    public String getJdbcUrl() {
        return getPostgresqlContainer().getJdbcUrl();
    }

    public String getUsername() {
        return getPostgresqlContainer().getUsername();
    }

    public String getPassword() {
        return getPostgresqlContainer().getPassword();
    }

    private PostgreSQLContainer getPostgresqlContainer() {
        return (PostgreSQLContainer) container;
    }

    public record Config(String image,
                         String databaseName,
                         String username,
                         String password,
                         String initScriptPath) {
    }
}
