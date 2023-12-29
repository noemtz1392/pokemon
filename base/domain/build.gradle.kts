plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    customImplementation(Dependencies.domain)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}