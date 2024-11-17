plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.remipics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.remipics"
        minSdk = 31
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
}

dependencies {

    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation(libs.appcompat)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.google.android.libraries.places:places:2.7.0") //api para busqueda de lugares

    // FusedLocationProviderClient para obtener la ubicación actual
    implementation ("com.google.android.gms:play-services-location:18.0.0")

    // Google Maps SDK
    implementation ("com.google.android.gms:play-services-maps:17.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.google.android.gms:play-services-auth:20.2.0")

}