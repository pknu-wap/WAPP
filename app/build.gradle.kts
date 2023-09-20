plugins {
    id("com.wap.wapp.application")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.wap.wapp"

    defaultConfig {
        applicationId = "com.wap.wapp"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(libs.bundles.androidx)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}
