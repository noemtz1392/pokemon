plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    customImplementation(Dependencies.domain)
    customImplementation(Dependencies.di)
    implementation("androidx.paging:paging-common:3.2.1")

    api(":base:domain")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}