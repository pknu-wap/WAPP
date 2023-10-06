pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WAPP"
include(":app")
include(":core")
include(":core:data")
include(":feature")
include(":feature:auth")
include(":core:domain")
include(":core:network")
include(":core:designsystem")
include(":core:designresource")
include(":core:base")
include(":feature:notice")
include(":feature:survey")
include(":feature:profile")
include(":feature:manage")
include(":feature:splash")
include(":core:model")