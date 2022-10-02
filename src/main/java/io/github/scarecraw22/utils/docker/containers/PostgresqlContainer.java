package io.github.scarecraw22.utils.docker.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlContainer extends BaseContainer {

    public PostgresqlContainer(String databaseName, String username, String password) {
        super(new PostgreSQLContainer("postgres:14.4-alpine")
                        .withDatabaseName(databaseName)
                        .withUsername(username)
                        .withPassword(password),
                "Postgresql");
    }

    public PostgresqlContainer(String databaseName, String username, String password, String initScriptPath) {
        super(new PostgreSQLContainer("postgres:14.4-alpine")
                        .withDatabaseName(databaseName)
                        .withUsername(username)
                        .withPassword(password)
                        .withInitScript(initScriptPath),
                "Postgresql");
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
}
