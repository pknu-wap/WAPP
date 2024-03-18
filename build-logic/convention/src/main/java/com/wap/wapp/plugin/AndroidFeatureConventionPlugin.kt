package com.wap.wapp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureConventionPlugin: Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.wap.wapp.library")
                apply("com.wap.wapp.compose")
                apply("com.wap.wapp.hilt")
                apply("com.wap.wapp.navigation")
            }
        }
    }
}
