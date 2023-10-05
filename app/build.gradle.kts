plugins {
    id("com.wap.wapp.application")
    id("com.wap.wapp.firebase")
    id("com.wap.wapp.compose")
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
    implementation(project(":feature:notice"))
    implementation(project(":feature:survey"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:manage"))
    implementation(project(":feature:splash"))
    implementation(project(":core:designresource"))

    implementation(libs.bundles.androidx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}

tasks.getByPath(":app:preBuild").dependsOn("installGitHook")

tasks.register<Copy>("installGitHook") {
    dependsOn("deletePreviousGitHook")
    from("${rootProject.rootDir}/script/pre-commit")
    into("${rootProject.rootDir}/.git/hooks")
    eachFile {
        fileMode = 777
    }
}

tasks.register<Delete>("deletePreviousGitHook") {

    val prePush = "${rootProject.rootDir}/.git/hooks/pre-commit"
    if (file(prePush).exists()) {
        delete(prePush)
    }
}