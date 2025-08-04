plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.compose") // 👈 Este es clave para Kotlin 2.0+
}


android {
    namespace = "com.example.macrotracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.macrotracker"
        minSdk = 26
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Versión compatible con Compose 1.5.x
    }
}

dependencies {
    // BOM para Compose, maneja versiones de todas las libs Compose
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")

    // Compose UI básico (contiene KeyboardOptions)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-text") // Necesario para KeyboardOptions, KeyboardType, etc.

    // Material3
    implementation("androidx.compose.material3:material3")

    // Otras dependencias
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // API
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}
