import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.lipen"

plugins {
    kotlin("jvm") version Versions.kotlin
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlint
    id("com.github.ben-manes.versions") version Versions.gradle_versions
    id("fr.brouillard.oss.gradle.jgitver") version Versions.jgitver
    id("org.jetbrains.dokka") version Versions.dokka
    id("com.eden.orchidPlugin") version Versions.orchid
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(Libs.junit_jupiter_api)
    testRuntimeOnly(Libs.junit_jupiter_engine)

    orchidRuntimeOnly(Libs.orchid_all)
    orchidRuntimeOnly(Libs.orchid_docs)
    orchidRuntimeOnly(Libs.orchid_kotlindoc)
    orchidRuntimeOnly(Libs.orchid_plugin_docs)
    orchidRuntimeOnly(Libs.orchid_asciidoc)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val dokkaJavadoc by tasks.creating(DokkaTask::class) {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/dokkaJavadoc"
}

val dokkaJavadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from(dokkaJavadoc)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        artifact(sourcesJar)
        artifact(dokkaJavadocJar)
    }
    repositories {
        maven(url = "$buildDir/repository")
    }
}

ktlint {
    ignoreFailures.set(true)
}

jgitver {
    strategy("MAVEN")
}

orchid {
    theme = "Editorial"
    args = listOf("--experimentalSourceDoc")

    if (findProperty("env") == "prod") {
        baseUrl = "https://lipen.github.io/MultiArray"
        environment = "prod"
    }

    githubToken = System.getenv("GITHUB_TOKEN")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        @Suppress("UnstableApiUsage")
        useJUnitPlatform()
        testLogging.events(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_ERROR
        )
    }

    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokkaHtml"
    }

    wrapper {
        gradleVersion = "6.1.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}

defaultTasks("clean", "build")
