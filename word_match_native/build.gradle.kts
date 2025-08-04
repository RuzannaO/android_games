plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.wordmatch.words"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wordmatch.words"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.keystore")
            storePassword = "narman"
            keyAlias = "my-key-alias"
            keyPassword = "narman"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    // Add more dependencies here as needed
}
signingConfigs {
    create("release") {
        storeFile = file(MYAPP_UPLOAD_STORE_FILE)
        storePassword = MYAPP_UPLOAD_STORE_PASSWORD
        keyAlias = MYAPP_UPLOAD_KEY_ALIAS
        keyPassword = MYAPP_UPLOAD_KEY_PASSWORD
    }
}

buildTypes {
    getByName("release") {
        isMinifyEnabled = false
        isShrinkResources = false
        signingConfig = signingConfigs.getByName("release")
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}