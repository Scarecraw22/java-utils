package io.github.scarecraw22.utils.docker

import org.postgresql.Driver
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

class PostgresqlContainerTest extends Specification {

    private static final PostgresqlContainer CONTAINER = new PostgresqlContainer("test", "username", "password")

    def setupSpec() {
        CONTAINER.startWithStopOnShutdown()
    }

    def cleanupSpec() {
        CONTAINER.stop()
    }

    def "Postgresql container should run without exception"() {
        when:
        Properties properties = new Properties()
        properties.put("user", CONTAINER.getUsername())
        properties.put("password", CONTAINER.getPassword())
        DriverManager.registerDriver(new Driver())
        Connection conn = DriverManager.getConnection(CONTAINER.getJdbcUrl(), properties)

        then:
        conn.getMetaData().getURL() != null

        cleanup:
        conn.close()
    }
}
