plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "uz.ibrohim.meat"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.ibrohim.meat"
        minSdk = 21
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Navigation Fragment
    implementation (libs.androidx.fragment)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigationUi)

    //Toast
    implementation (libs.toast.grender)

    //Progress
    implementation(libs.progress.colakcode)

    //Dialog
    implementation(libs.dialog.umeshJangid)

    //Camera
    implementation(libs.imagepicker.dhaval2404)

    //Glide
    annotationProcessor(libs.glide.compiler)
    implementation(libs.glide.bumptech)

    //ViewModel
    implementation(libs.viewModel.lifecycle)
    implementation(libs.viewModel.runtime)

    //Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.converter.retrofit)

    // interceptor
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}