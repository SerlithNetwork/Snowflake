import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer

plugins {
    java
    id("com.gradleup.shadow") version "8.3.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.sigpipe:jbsdiff:1.0")
    implementation("com.google.jimfs:jimfs:1.3.0")
}

tasks.shadowJar {
    val prefix = "net.serlith.snowflake.libs"
    listOf("org.apache", "org.tukaani", "io.sigpipe", "com.google").forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }

    transform(ServiceFileTransformer::class.java) {
        setPath("META-INF/services/java.nio.file.spi.FileSystemProvider")
        relocate("com.google", "$prefix.com.google")
    }

    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/NOTICE.txt")
    exclude("javax/annotation/**")
    exclude("org/checkerframework/**")
}
