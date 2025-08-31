package com.spectrum.crimson

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableRedisRepositories(basePackages = ["com.spectrum.crimson.domain.redis.repository"])
class CrimsonServerApplication

fun main(args: Array<String>) {
    runApplication<CrimsonServerApplication>(*args)
}
