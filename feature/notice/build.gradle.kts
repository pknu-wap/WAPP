plugins {
    id("com.wap.wapp.feature")
}

android {
    namespace = "com.wap.wapp.feature.notice"

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
    implementation(project(":core:designresource"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(libs.bundles.androidx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("io.mockk:mockk:1.13.5")
}
