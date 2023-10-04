package com.wap.wapp.plugin.configure

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(){
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extensions.getByType<BaseExtension>().apply {
        buildFeatures.compose = true

        composeOptions.kotlinCompilerExtensionVersion =
            libs.findVersion("compose-compiler").get().toString()
    }
}
