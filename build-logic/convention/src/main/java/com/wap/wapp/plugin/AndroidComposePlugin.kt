package com.wap.wapp.plugin

import com.wap.wapp.plugin.configure.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies{
                "implementation"(libs.findBundle("compose").get())
                "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            }
            configureAndroidCompose()
        }
    }
}