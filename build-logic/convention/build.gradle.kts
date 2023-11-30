plugins{
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        create("androidApplication") {
            id = "com.wap.wapp.application"
            implementationClass = "com.wap.wapp.plugin.AndroidApplicationPlugin"
        }
        create("androidLibrary") {
            id = "com.wap.wapp.library"
            implementationClass = "com.wap.wapp.plugin.AndroidLibraryPlugin"
        }
        create("androidFirebase") {
            id = "com.wap.wapp.firebase"
            implementationClass = "com.wap.wapp.plugin.AndroidApplicationFirebasePlugin"
        }
        create("androidFeatureConvention") {
            id = "com.wap.wapp.feature"
            implementationClass = "com.wap.wapp.plugin.AndroidFeatureConventionPlugin"
        }
        create("androidCompose") {
            id = "com.wap.wapp.compose"
            implementationClass = "com.wap.wapp.plugin.AndroidComposePlugin"
        }
        create("androidHilt") {
            id = "com.wap.wapp.hilt"
            implementationClass = "com.wap.wapp.plugin.AndroidHiltPlugin"
        }
        create("androidNavigation") {
            id = "com.wap.wapp.navigation"
            implementationClass = "com.wap.wapp.plugin.AndroidNavigationPlugin"
        }
    }
}
