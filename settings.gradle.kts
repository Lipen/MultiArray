rootProject.name = "MultiArray"

plugins {
    id("com.gradle.enterprise") version "3.5"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
