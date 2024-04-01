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
include(":core:domain")
include(":core:network")
include(":core:designsystem")
include(":core:designresource")
include(":core:model")
include(":core:common")
include(":core:analytics")
include(":feature")
include(":feature:auth")
include(":feature:notice")
include(":feature:survey")
include(":feature:profile")
include(":feature:management")
include(":feature:splash")
include(":feature:management-survey")
include(":feature:management-event")
include(":feature:survey-check")
include(":feature:attendance")
