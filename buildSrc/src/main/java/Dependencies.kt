object Dependencies {

    object Compose {
        const val compose_version = "1.4.0"
        const val compose_compiler_version = "1.4.4"

        const val ui = "androidx.compose.ui:ui:$compose_version"
        const val tooling = "androidx.compose.ui:ui-tooling-preview:$compose_version"
        const val composeManifest = "androidx.compose.ui:ui-test-manifest:$compose_version"
        const val livedata = "androidx.compose.runtime:runtime-livedata:$compose_version"

        const val toolingTest = "androidx.compose.ui:ui-tooling:$compose_version"
        const val uiTest = "androidx.compose.ui:ui-test-junit4:$compose_version"

        const val composeDestinations = "1.8.38-beta"
        const val destinations = "io.github.raamcosta.compose-destinations:core:$composeDestinations"
        const val destinationKsp = "io.github.raamcosta.compose-destinations:ksp:$composeDestinations"

        object Dialogs { // https://github.com/vanpra/compose-material-dialogs
            const val version = "0.9.0"
            const val vanpra_core = "io.github.vanpra.compose-material-dialogs:core:$version"
            const val vanpra_datetime = "io.github.vanpra.compose-material-dialogs:datetime:$version"
        }

        object Accompanist {
            private const val version = "0.31.0-alpha"
            const val permissions = "com.google.accompanist:accompanist-permissions:$version"
            const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        }
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:31.5.0"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val messaging = "com.google.firebase:firebase-messaging"
    }
    object Android {
        const val icons_version = "1.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:1.8.20"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val runtimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
        const val material3 = "androidx.compose.material3:material3:1.1.0-beta02"
        const val icons = "androidx.compose.material:material-icons-core:$icons_version"
        const val iconsExtended = "androidx.compose.material:material-icons-extended:$icons_version"
        const val splash = "androidx.core:core-splashscreen:1.0.0"
    }

    object Lifecycle {
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val activityCompose = "androidx.activity:activity-compose:1.6.1"
    }

    object Navigation {
        const val navigationCompose = "androidx.navigation:navigation-compose:2.5.3"
    }

    object Test {
        const val jUnit = "junit:junit:4.+"
        const val androidJUnit = "androidx.test.ext:junit:1.1.5"
        const val androidJUnitKtx = "androidx.test.ext:junit-ktx:1.1.5"
        const val robolectric = "org.robolectric:robolectric:4.9.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        const val core = "androidx.arch.core:core-testing:2.1.0"
        const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
        const val mockk = "io.mockk:mockk:1.13.3"
    }

    object Hilt {
        const val version = "2.44.2"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0"

        object Test {
            const val testVersion = "2.44"
            const val androidTest = "com.google.dagger:hilt-android-testing:$testVersion"
            const val androidCompiler = "com.google.dagger:hilt-android-compiler:$testVersion"
        }
    }

    object Coroutines {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Network {
        private const val okHttpVersion = "4.10.0"
        private const val retrofitVersion = "2.9.0"
        const val gson = "com.google.code.gson:gson:$retrofitVersion"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okhttp3 = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    }

    object Room {
        private const val version = "2.5.0"
        const val ktx = "androidx.room:room-ktx:$version"
        const val runtime = "androidx.room:room-runtime:$version"
        const val paging = "androidx.room:room-paging:$version"
        const val compiler = "androidx.room:room-compiler:$version"
    }

    object Files {
        const val commons = "commons-io:commons-io:2.11.0"
    }

    object Images {
        const val coil = "io.coil-kt:coil-compose:2.2.2"
    }

    object BackwardCompatibility {
        const val desugar = "com.android.tools:desugar_jdk_libs:1.1.6"
    }

    object Accompanist {
        private const val version = "0.28.0"
        const val accompanistPermission = "com.google.accompanist:accompanist-permissions:$version"
        const val accompanistSwiperefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object Project {
        const val domain = ":domain"
        const val data = ":data"
        const val error = ":error"
    }
}