plugins {
    id("com.wap.wapp.library")
    id("com.wap.wapp.hilt")
    id("com.wap.wapp.compose")
}

android {
    namespace = "com.wap.wapp.core.analytics"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}
