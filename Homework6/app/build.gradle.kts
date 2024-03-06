plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.homework6"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.homework6"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "OPEN_WEATHER_BASE_URL",
            "\"https://api.openweathermap.org/data/2.5/\""
        )

        buildConfigField(
            "String",
            "OPEN_WEATHER_IMG_BASE_URL",
            "\"https://openweathermap.org/img/wn/\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }



    buildFeatures {
        viewBinding = true
        buildConfig = true
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
}

dependencies {

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    val coreCtxVersion = "1.9.0"
    implementation("androidx.core:core-ktx:$coreCtxVersion")

    val appcompatVersion = "1.6.1"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    val fragmentKtxVersion = "1.6.2"
    implementation("androidx.fragment:fragment-ktx:$fragmentKtxVersion")

    val androidMaterialVersion = "1.9.0"
    implementation("com.google.android.material:material:$androidMaterialVersion")

    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    "kapt"("androidx.room:room-compiler:$roomVersion")

    val coroutinesVersion = "1.3.9"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

    val gsonVersion = "2.10.1"
    implementation("com.google.code.gson:gson:$gsonVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    val loggingVersion = "4.12.0"
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingVersion")

    val picassoVersion = "2.8"
    implementation ("com.squareup.picasso:picasso:${picassoVersion}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}