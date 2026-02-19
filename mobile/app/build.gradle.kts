plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cook_camplus"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.cook_camplus"
        minSdk = 24
        targetSdk = 36
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
    
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    
    // OkHttp for networking
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    
    // Coil for image loading
    implementation(libs.coil)
    
    // Security for EncryptedSharedPreferences
    implementation(libs.security.crypto)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}