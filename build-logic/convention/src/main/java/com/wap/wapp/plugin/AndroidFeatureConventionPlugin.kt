package com.wap.wapp.plugin

import com.wap.wapp.plugin.configure.configureBinding
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureConventionPlugin: Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.wap.wapp.library")
                apply("com.wap.wapp.compose")
            }
            configureBinding()
        }
    }
}