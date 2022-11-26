import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.Lipen"

plugins {
    kotlin("jvm") version Versions.kotlin
    idea
    `maven-publish`
    with(Plugins.Kotlinter) { id(id) version version }
    with(Plugins.GradleVersions) { id(id) version version }
    with(Plugins.Jgitver) { id(id) version version }
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(Libs.JUnit.jupiter_api)
    testRuntimeOnly(Libs.JUnit.jupiter_engine)
    testImplementation(Libs.Kluent.kluent)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_ERROR
        )
    }
}

kotlinter {
    ignoreFailures = true
    experimentalRules = true
    disabledRules = arrayOf("import-ordering")
}

java {
    @Suppress("UnstableApiUsage")
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven(url = "$buildDir/repository")
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

jgitver {
    strategy("MAVEN")
}

tasks.wrapper {
    gradleVersion = "7.6"
    distributionType = Wrapper.DistributionType.ALL
}

defaultTasks("clean", "build")
