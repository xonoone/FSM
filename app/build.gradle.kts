plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") version ("1.8.10-1.0.9")
    kotlin("plugin.parcelize")
    kotlin("android")
    kotlin("kapt") version ("1.8.10")
    id("com.google.gms.google-services")
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
    namespace = Config.packageName
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSDK
        targetSdk = Config.targetSDK
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        debug {
//            isMinifyEnabled = true
//            isShrinkResources = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compose_compiler_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}


dependencies {
    // Firebase
    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.analytics)
    implementation(Dependencies.Firebase.messaging)
    // Project
    implementation(project(Dependencies.Project.data))
    implementation(project(Dependencies.Project.domain))
    implementation(project(Dependencies.Project.error))

    // Android
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material3)
    implementation(Dependencies.Android.runtimeCompose)
    implementation(Dependencies.Android.icons)
    implementation(Dependencies.Android.iconsExtended)
    implementation(Dependencies.Android.splash)

    // Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.composeManifest)
    implementation(Dependencies.Compose.livedata)
    // Dialogs
    implementation(Dependencies.Compose.Dialogs.vanpra_core)
    implementation(Dependencies.Compose.Dialogs.vanpra_datetime)
    // Accompanist
    implementation(Dependencies.Compose.Accompanist.permissions)
    implementation(Dependencies.Compose.Accompanist.swipeRefresh)

    // Lifecycle
    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)
    implementation(Dependencies.Lifecycle.activityCompose)

    // Coroutines
    implementation(Dependencies.Coroutines.coroutines)

    // Navigation
    implementation(Dependencies.Navigation.navigationCompose)
    implementation(Dependencies.Compose.destinations)
    ksp(Dependencies.Compose.destinationKsp)

    // Hilt
    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    kapt(Dependencies.Hilt.compiler)

    // Files
    implementation(Dependencies.Files.commons)

    // Images
    implementation(Dependencies.Images.coil)

    // Tests
    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.core)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.androidJUnitKtx)
    testImplementation(Dependencies.Test.robolectric)
    androidTestImplementation(Dependencies.Test.androidJUnit)
    androidTestImplementation(Dependencies.Test.espresso)

    androidTestImplementation(Dependencies.Compose.uiTest)
    debugImplementation(Dependencies.Compose.toolingTest)

    testImplementation(Dependencies.Hilt.Test.androidTest)
    kaptTest(Dependencies.Hilt.Test.androidCompiler)
    androidTestImplementation(Dependencies.Hilt.Test.androidTest)
    kaptAndroidTest(Dependencies.Hilt.Test.androidCompiler)
}