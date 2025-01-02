plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.9")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}