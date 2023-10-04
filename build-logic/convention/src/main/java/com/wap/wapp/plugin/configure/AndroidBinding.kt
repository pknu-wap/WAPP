package com.wap.wapp.plugin.configure

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

fun Project.configureBinding(){
    extensions.getByType<BaseExtension>().apply {
        buildFeatures.apply {
            viewBinding = true
            dataBinding.enable = true
        }
    }
}