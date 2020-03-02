plugins {
    groovy
    `java-gradle-plugin`
    `build-scan`
    org.gradle.playframework.`test-setup`
    org.gradle.playframework.`integration-test-fixtures`
    org.gradle.playframework.`integration-test`
    org.gradle.playframework.`user-guide`
    org.gradle.playframework.`github-pages`
    org.gradle.playframework.`documentation-test`
    id("com.gradle.plugin-publish") version "0.10.1"
}

group = "org.gradle.playframework"
version = "0.10"

repositories {
    jcenter()
    maven(url = "https://repo.gradle.org/gradle/libs")
}

dependencies {
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5") {
        exclude(group = "org.codehaus.groovy")
    }
    integTestFixturesImplementation("com.google.guava:guava:23.0")
    integTestFixturesImplementation("org.hamcrest:hamcrest-library:1.3")
    integTestFixturesImplementation("org.apache.ant:ant:1.9.3")
    docTestImplementation("org.gradle:sample-check:0.7.0")
    docTestRuntimeOnly("org.slf4j:slf4j-simple:1.7.16")
}

java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

gradlePlugin {
    testSourceSets(sourceSets.integTest.get(), sourceSets.docTest.get())

    plugins {
        register("play-twirl-plugin") {
            id = "org.gradle.playframework-twirl"
            displayName = "Play Twirl Plugin"
            description = "Plugin for compiling Twirl sources in a Play application."
            implementationClass = "org.gradle.playframework.plugins.PlayTwirlPlugin"
        }

        register("play-routes-plugin") {
            id = "org.gradle.playframework-routes"
            displayName = "Play Routes Plugin"
            description = "Plugin for compiling Play routes sources in a Play application."
            implementationClass = "org.gradle.playframework.plugins.PlayRoutesPlugin"
        }

        register("play-application-plugin") {
            id = "org.gradle.playframework-application"
            displayName = "Play Application Plugin"
            description = "Plugin for building a Play application."
            implementationClass = "org.gradle.playframework.plugins.PlayApplicationPlugin"
        }

        register("play-javascript-plugin") {
            id = "org.gradle.playframework-javascript"
            displayName = "Play JavaScript Plugin"
            description = "Plugin for adding JavaScript processing to a Play application."
            implementationClass = "org.gradle.playframework.plugins.PlayJavaScriptPlugin"
        }

        register("play-distribution-plugin") {
            id = "org.gradle.playframework-distribution"
            displayName = "Play Distribution Plugin"
            description = "Plugin for generating distributions for Play applications."
            implementationClass = "org.gradle.playframework.plugins.PlayDistributionPlugin"
        }

        register("play-ide-plugin") {
            id = "org.gradle.playframework-ide"
            displayName = "Play IDE Plugin"
            description = "Plugin for generating IDE project files for Play applications."
            implementationClass = "org.gradle.playframework.plugins.PlayIdePlugin"
        }

        register("play-plugin") {
            id = "org.gradle.playframework"
            displayName = "Play Plugin"
            description = "Plugin that supports building, testing and running Play applications with Gradle."
            implementationClass = "org.gradle.playframework.plugins.PlayPlugin"
        }
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    if (!System.getenv("CI").isNullOrEmpty()) {
        publishAlways()
        tag("CI")
    }
}