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
    ":base:domain"
)

include(
    ":features:list:data",
    ":features:list:domain",
    ":features:list:ui"
)
include(":base:ui")
