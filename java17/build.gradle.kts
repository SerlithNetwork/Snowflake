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
}

tasks.shadowJar {
    val prefix = "net.serlith.snowflake.libs"
    listOf("org.apache", "org.tukaani", "io.sigpipe").forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
    relocate("io.papermc.paperclip", "net.serlith.snowflake")

    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/NOTICE.txt")
}
