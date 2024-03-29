package com.wap.wapp.plugin

import com.wap.wapp.plugin.configure.configureApplicationVersion
import com.wap.wapp.plugin.configure.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            configureKotlinAndroid()
            configureApplicationVersion()
        }
    }
}
