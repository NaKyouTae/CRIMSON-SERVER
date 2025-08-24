package com.spectrum.crimsonserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrimsonServerApplication

fun main(args: Array<String>) {
    runApplication<CrimsonServerApplication>(*args)
}
