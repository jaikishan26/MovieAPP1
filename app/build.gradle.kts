plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.movieapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //for rememberLazyListState()
    //implementation("androidx.compose.foundataion:foundation:1.5.0")
    //implementation(libs.androidx.foundation)


    //implementation("androidx.room:room-runtime:2.6.0")
    implementation(libs.androidx.room.runtime)


    implementation("androidx.room:room-ktx:2.6.0")
    implementation(libs.androidx.navigation.compose)
    //implementation(libs.androidx.room.ktx)


    //implementation("androidx.room:room-compiler:2.6.0")
    //implementation(libs.androidx.room.compiler)
    kapt("androidx.room:room-compiler:2.6.0")

    //Compose viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    //implementation(libs.androidx.lifecycle.viewmodel.savedstate.compose)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //Network call
    //implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.retrofit)

    //implementation(libs.retrofit)


    //Json to kotlin Object mapping
    //implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.converter.gson)

    //Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")
    //implementation(libs.coil.compose)

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    //implementation(libs.navigation.fragment.ktx)
    //implementation(libs.navigation.ui.ktx)

   // implementation(libs.androidx.navigation)

    //Dagger
    //implementation("com.google.dagger:dagger:2.41")
    //implementation(libs.dagger)
    //kapt(libs.dagger.compiler)

        implementation("com.google.dagger:dagger:2.48")
       kapt("com.google.dagger:dagger-compiler:2.48")


    //logging
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}