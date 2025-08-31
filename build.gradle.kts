import com.google.protobuf.gradle.id

plugins {
    val kotlinVersion = "1.9.25"
    val springBootVersion = "3.5.6-SNAPSHOT"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("com.google.protobuf") version "0.9.4"
    id("org.liquibase.gradle") version "2.0.4"
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.spectrum"
version = "0.0.1-SNAPSHOT"
description = "crimson-server"
val protobufVersion = "4.29.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.jsonwebtoken:jjwt-api:0.12.1")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.1")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.1")

    implementation("org.liquibase:liquibase-core:4.31.0")

    implementation("com.github.f4b6a3:ulid-creator:5.2.3")
    implementation("org.liquibase:liquibase-core:4.31.0")
    implementation("org.postgresql:postgresql:42.7.7")

    implementation("com.google.protobuf:protobuf-kotlin:${protobufVersion}")
    implementation("com.google.protobuf:protobuf-java:${protobufVersion}")
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")

    runtimeOnly ("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
    }
}

sourceSets {
    main {
//        proto {
//            srcDir("src/main/proto")
//        }
        java {
            srcDir("build/generated/source/proto/main/java")
        }
    }

    test {
//        proto {
//            srcDir("src/main/proto")
//        }
        java {
            srcDir("build/generated/source/proto/test/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("kotlin")
            }
        }
    }
}
