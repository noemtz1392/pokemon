plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    customImplementation(Dependencies.domain)
    customImplementation(Dependencies.di)

    api(":base:domain")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}