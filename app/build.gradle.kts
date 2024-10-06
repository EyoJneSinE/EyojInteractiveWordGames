plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.gms.googleServices)
}

android {
    namespace = "com.eniskaner.eyojinteractivewordgames"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eniskaner.eyojinteractivewordgames"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlin {
        sourceSets.main {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // hilt
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    annotationProcessor (libs.hilt.android.compiler)
    implementation (libs.hilt.android.compose)
    /*implementation(libs.dagger.hilt)
    implementation(libs.dagger.compiler)
    implementation(libs.dagger.hilt.compiler)*/
    //ksp(libs.dagger.hilt.compiler)
    //ksp(project(":test-processor"))
    //ksp(libs.dagger.compiler)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material.material3)
    implementation(libs.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    //implementation "com.google.code.gson:gson:2.10.1"
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    //OkHttp
    implementation(libs.okHttp)
    //Gson
    implementation(libs.gson)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    // Swipe Refresh Layout
    implementation(libs.swiperefreshlayout)
    // Multidex
    implementation(libs.multidex)
    //ksp
    implementation(libs.ksp)
    implementation(libs.ksp.api)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.mlkit.translate)
    //Room Database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(kotlin("reflect"))
}