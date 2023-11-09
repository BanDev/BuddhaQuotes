import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "org.bandev.buddhaquotes"

    compileSdk = 34

    defaultConfig {
        applicationId = "org.bandev.buddhaquotes"
        minSdk = 21
        targetSdk = 34
        versionCode = 1015
        versionName = "3.0.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        abortOnError = false
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.browser)
    implementation(libs.core.ktx)
    implementation(libs.material3)
    implementation(libs.runtime.livedata)
    implementation(libs.activity.compose)
    implementation(libs.material.icons.extended)
    implementation(libs.animation)
    implementation(libs.navigation.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // AboutLibraries - https://github.com/mikepenz/AboutLibraries
    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose)

    // Compose Settings - https://github.com/alorma/Compose-Settings
    implementation(libs.settings.ui)

    // Datastore - https://developer.android.com/topic/libraries/architecture/datastore
    implementation(libs.datastore)
    implementation(libs.protobuf.javalite)

    // Hilt - https://developer.android.com/training/dependency-injection/hilt-android
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // Kotlin Coroutines - https://github.com/Kotlin/kotlinx.coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Lifecycle - https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Lottie Compose - https://github.com/airbnb/lottie
    implementation(libs.lottie.compose)

    // Room - https://developer.android.com/training/data-storage/room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Sheets - https://github.com/maxkeppeler/sheets-compose-dialogs
    implementation(libs.sheets.core)
    implementation(libs.sheets.duration)
    implementation(libs.sheets.info)
    implementation(libs.sheets.input)
    implementation(libs.sheets.option)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
