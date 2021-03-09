val gsonVersion: String by project
val slf4jVersion: String by project

plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("java")
    id("com.github.sherter.google-java-format") version "0.8" apply true
}

repositories {
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api:${slf4jVersion}");
    implementation("org.slf4j:slf4j-simple:${slf4jVersion}")
    implementation("com.google.code.gson:gson:${gsonVersion}")

    implementation("org.ow2.asm:asm-commons:8.0.1")
    implementation("org.ow2.asm:asm-util:8.0.1")
//    testImplementation("org.testng:testng:${testNgVersion}")
}

tasks.shadowJar {
    archiveClassifier.set("")
    manifest {
        attributes(
                "Premain-Class" to "com.newrelic.otlp.Agent",
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "New Relic, Inc."
        )
    }
}

tasks.named("build") {
    dependsOn("shadowJar")
}