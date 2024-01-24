plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}







android {
    namespace = "com.example.streamingamazing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.streamingamazing"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

     //Glass morphic
    implementation ("com.github.jakhongirmadaminov:glassmorphic-composables:0.0.3")




    //android youtube player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")


    //moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


    //shared preferences
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //google sigin
    implementation ("com.google.android.gms:play-services-auth:19.2.0")



    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.4")

    //BottomNavigation
    implementation("androidx.compose.material:material:1.5.3")

    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")


    //hilt navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    //Cortines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")


    //Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    kapt("com.google.dagger:hilt-android-compiler:2.44")


    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //rxjava
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")

    //rx adapter
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}