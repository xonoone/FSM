plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt") version ("1.8.10")
}

repositories {
    google()
    mavenCentral()
}

// Pure Kotlin
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
// Pure Java
tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

android {
    namespace = "${Config.packageName}.data"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        debug {
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // Project
    implementation(project(Dependencies.Project.domain))
    implementation(project(Dependencies.Project.error))

    // Hilt
    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    // Network
    implementation(Dependencies.Network.gson)
    implementation(Dependencies.Network.retrofit2)
    implementation(Dependencies.Network.retrofit2_converter_gson)
    implementation(Dependencies.Network.okhttp3)
    implementation(Dependencies.Network.okHttpInterceptor)

    // Room
    implementation(Dependencies.Room.ktx)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.paging)
    kapt(Dependencies.Room.compiler)

    // Mapper
    implementation(Dependencies.Android.kotlin_reflect)

    // Tests
    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.mockk)
}