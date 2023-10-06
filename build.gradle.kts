buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.build)
        classpath(libs.kotlin.gradle)
        classpath(libs.hilt.gradle)
        classpath(libs.google.services.gradle)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.ksp) apply false
}