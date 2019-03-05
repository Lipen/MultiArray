import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.ifmo.multiarray"

plugins {
    kotlin("jvm") version "1.3.21"
    `build-scan`
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jlleitschuh.gradle.ktlint") version "7.1.0"
    id("fr.brouillard.oss.gradle.jgitver") version "0.8.0"
    id("org.jetbrains.dokka") version "0.9.17"
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
        maven {
            this.url = uri("$buildDir/repository")
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true
    override = false
    setPublications("maven")
    with(pkg) {
        userOrg = "lipen"
        repo = "maven"
        name = "multiarray"
        vcsUrl = "https://github.com/Lipen/MultiArray.git"
        setLabels("kotlin", "multidimensional", "array")
        setLicenses("GPL-3.0")
        with(version) {
            desc = "Multidimensional array for Kotlin"
            with(gpg) {
                sign = true
                passphrase = System.getenv("BINTRAY_GPG_PASSWORD")
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

    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    wrapper {
        gradleVersion = "5.2.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
