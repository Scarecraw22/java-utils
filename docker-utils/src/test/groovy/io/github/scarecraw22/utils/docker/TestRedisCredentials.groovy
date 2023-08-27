package io.github.scarecraw22.utils.docker

import redis.clients.jedis.RedisCredentials

class TestRedisCredentials implements RedisCredentials {

    TestRedisCredentials() {}

    @Override
    String getUser() {
        return super.getUser()
    }

    @Override
    char[] getPassword() {
        return super.getPassword()
    }
}
