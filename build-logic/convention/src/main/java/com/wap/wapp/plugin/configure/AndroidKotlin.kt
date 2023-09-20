package com.wap.wapp.plugin.configure

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(){
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extensions.getByType<BaseExtension>().apply {
        setCompileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        (this as ExtensionAware).configure<KotlinJvmOptions> {
            jvmTarget = "17"
        }
    }
}