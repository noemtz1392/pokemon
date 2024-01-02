pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "pokemon"

include(":app")

include(
    ":base:data",
    ":base:domain",
    ":base:ui"
)

include(
    ":features:list:data",
    ":features:list:domain",
    ":features:list:ui"
)