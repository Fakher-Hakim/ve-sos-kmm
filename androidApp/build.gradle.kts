plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.bridge.softwares.vesos"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.bridge.softwares.vesos"
        minSdk = 24
        targetSdk = 33
        versionCode = 4
        versionName = "1.0.1"
    }
    buildFeatures {
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0")
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("com.github.gcacace:signature-pad:1.3.1")

    implementation(project.dependencies.platform("com.google.firebase:firebase-bom:29.0.4"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}