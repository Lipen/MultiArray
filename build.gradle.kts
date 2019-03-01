import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.ifmo.multiarray"
version = "0.1.0"

plugins {
    kotlin("jvm") version "1.3.21"
    `build-scan`
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jlleitschuh.gradle.ktlint") version "7.1.0"
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

bintray {
    user = (findProperty("bintray.user") ?: System.getenv("BINTRAY_USER"))?.toString()
    key = (findProperty("bintray.key") ?: System.getenv("BINTRAY_KEY"))?.toString()
    setPublications("mavenJava")
    with(pkg) {
        repo = "MultiArray"
        name = "ru.ifmo.multiarray"
        setLicenses("GPL-3.0")
        vcsUrl = "https://github.com/Lipen/MultiArray.git"
        setLabels("multidimensional", "array")
        with(version) {
            name = project.version.toString()
            vcsTag = project.version.toString()
        }
    }
    publish = (project.findProperty("bintray.publish") ?: "true").toString().toBoolean()
    override = (project.findProperty("bintray.override") ?: "false").toString().toBoolean()
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
