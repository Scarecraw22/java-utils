package io.github.scarecraw22.utils.docker.containers

import io.github.scarecraw22.utils.file.FileUtils
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisClientConfig
import spock.lang.Specification

class RedisContainerTest extends Specification {

    private static final RedisContainer CONTAINER = new RedisContainer(FileUtils.getFileFromResources("redis/redis.conf"))

    def setupSpec() {
        CONTAINER.startWithStopOnShutdown()
    }

    def cleanupSpec() {
        CONTAINER.stop()
    }

    def "Redis container should run without exception"() {
        given:
        JedisClientConfig jedisClientConfig = Mock()
        jedisClientConfig.getPassword() >> "sample-password"
        Jedis jedis = new Jedis(CONTAINER.getContainerHostAddress(), CONTAINER.getFirstMappedPort(), jedisClientConfig)

        when:
        jedis.set("key", "value")

        then:
        noExceptionThrown()
        jedis.get("key") == "value"

        when:
        jedis.close()

        then:
        noExceptionThrown()
    }
}
