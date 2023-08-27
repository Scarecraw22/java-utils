package io.github.scarecraw22.utils.docker

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisClientConfig
import spock.lang.Specification

class RedisContainerTest extends Specification {

    private static final RedisContainer CONTAINER = new RedisContainer(
            new RedisContainer.Config(
                    null,
                    "redis/redis.conf",
                    null
            )
    )

    def setupSpec() {
        CONTAINER.startWithStopOnShutdown()
    }

    def cleanupSpec() {
        CONTAINER.stop()
    }

    def "Redis container should run without exception"() {
        given:
        JedisClientConfig jedisClientConfig = Mock()
        jedisClientConfig.getCredentialsProvider() >> new TestRedisCredentialsSupplier()
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
