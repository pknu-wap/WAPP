plugins{
    `kotlin-dsl`
    id("groovy-gradle-plugin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
    }
}
