@file:Suppress("PublicApiImplicitType", "MemberVisibilityCanBePrivate")

object Versions {
    const val genikos = "0.1.0"
    const val gradle_versions = "0.44.0"
    const val jgitver = "0.9.1"
    const val junit = "5.7.0"
    const val kotlin = "1.7.21"
    const val kotlinter = "3.12.0"
}

object Libs {
    // https://github.com/junit-team/junit5
    object JUnit {
        const val version = Versions.junit
        const val jupiter_api = "org.junit.jupiter:junit-jupiter-api:$version"
        const val jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:$version"
        const val jupiter_params = "org.junit.jupiter:junit-jupiter-params:$version"
    }

    // https://github.com/Lipen/genikos
    object Genikos {
        const val version = Versions.genikos
        const val genikos = "com.github.Lipen:genikos:$version"
    }
}

object Plugins {
    // https://github.com/jgitver/gradle-jgitver-plugin
    object Jgitver {
        const val version = Versions.jgitver
        const val id = "fr.brouillard.oss.gradle.jgitver"
    }

    // https://github.com/ben-manes/gradle-versions-plugin
    object GradleVersions {
        const val version = Versions.gradle_versions
        const val id = "com.github.ben-manes.versions"
    }

    // https://github.com/JLLeitschuh/ktlint-gradle
    object Kotlinter {
        const val version = Versions.kotlinter
        const val id = "org.jmailen.kotlinter"
    }
}
