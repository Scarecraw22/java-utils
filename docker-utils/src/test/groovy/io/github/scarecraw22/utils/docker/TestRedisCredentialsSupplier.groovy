package io.github.scarecraw22.utils.docker

import redis.clients.jedis.RedisCredentials

import java.util.function.Supplier

class TestRedisCredentialsSupplier implements Supplier<RedisCredentials> {
    @Override
    RedisCredentials get() {
        return new TestRedisCredentials()
    }
}
