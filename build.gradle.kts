plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.example.hms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hms"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase Authentication and Storage
    implementation("com.google.firebase:firebase-auth:21.0.5")
    implementation("com.google.firebase:firebase-storage:20.0.0")

    // CameraX Dependencies for Video Recording
    implementation("androidx.camera:camera-core:1.2.0")
    implementation("androidx.camera:camera-camera2:1.2.0")
    implementation("androidx.camera:camera-lifecycle:1.2.0")
    implementation("androidx.camera:camera-video:1.2.0")
    implementation("androidx.camera:camera-view:1.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.activity:activity-ktx:1.6.0")

    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("org.json:json:20210307")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.json:json:20210307")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:retrofit:2.x.x")
    implementation("com.squareup.retrofit2:converter-gson:2.x.x")



}
apply(plugin = "com.google.gms.google-services")
