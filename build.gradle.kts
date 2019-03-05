import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.ifmo.multiarray"

plugins {
    kotlin("jvm") version "1.3.21"
    `build-scan`
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jlleitschuh.gradle.ktlint") version "7.1.0"
    id("fr.brouillard.oss.gradle.jgitver") version "0.8.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val publicationName = "multiarray"
publishing {
    publications {
        create<MavenPublication>(publicationName) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

fun getProp(propName: String, envVar: String): String? =
    (findProperty(propName) ?: System.getenv(envVar))?.toString()

bintray {
    user = getProp("bintray.user", "BINTRAY_USER")
    key = getProp("bintray.key", "BINTRAY_KEY")
    publish = true
    override = false
    setPublications(publicationName)
    with(pkg) {
        repo = "MultiArray"
        name = "multiarray"
        vcsUrl = "https://github.com/Lipen/MultiArray.git"
        setLabels("kotlin", "multidimensional", "array")
        setLicenses("GPL-3.0")
        with(version) {
            desc = "Multidimensional array for Kotlin"
            with(gpg) {
                sign = true
                passphrase = getProp("bintray.gpg.password", "BINTRAY_GPG_PASSWORD")
            }
        }
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

ktlint {
    ignoreFailures.set(true)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "5.2.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
