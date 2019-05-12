import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.lipen"

plugins {
    kotlin("jvm") version "1.3.31"
    `build-scan`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "8.0.0"
    id("fr.brouillard.oss.gradle.jgitver") version "0.8.0"
    id("org.jetbrains.dokka") version "0.9.18"
    id("com.eden.orchidPlugin") version "0.16.10"
    id("com.github.ben-manes.versions") version "0.21.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.0")

    orchidRuntime("io.github.javaeden.orchid:OrchidDocs:0.16.10")
    orchidRuntime("io.github.javaeden.orchid:OrchidKotlindoc:0.16.10")
    orchidRuntime("io.github.javaeden.orchid:OrchidPluginDocs:0.16.10")
    orchidRuntime("io.github.javaeden.orchid:OrchidAsciidoc:0.16.10")
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

val dokkaJar by tasks.registering(Jar::class) {
    from(tasks.dokka)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(dokkaJar.get())
        }
    }
    repositories {
        maven(url = "$buildDir/repository")
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

ktlint {
    ignoreFailures.set(true)
}

jgitver {
    strategy("MAVEN")
}

orchid {
    theme = "Editorial"
    githubToken = System.getenv("GITHUB_TOKEN")

    if (project.hasProperty("env") && project.property("env") == "prod") {
        baseUrl = "https://lipen.github.io/MultiArray"
        environment = "prod"
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging.events(
            TestLogEvent.FAILED,
            // TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_ERROR
        )
    }

    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    wrapper {
        gradleVersion = "5.4.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

defaultTasks("clean", "build")
