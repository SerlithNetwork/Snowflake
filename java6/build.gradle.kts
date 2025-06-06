plugins {
    java
    id("com.gradleup.shadow") version "8.3.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }

    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(6)
    options.compilerArgs = listOf("-Xlint:-options")
}

tasks.shadowJar {
    relocate("io.papermc.paperclip", "net.serlith.snowflake")
}
