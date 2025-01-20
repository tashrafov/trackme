plugins {
    alias(libs.plugins.baseproject.android.library)
    id("kotlin-kapt")
}

android {
    namespace = "com.taghiashrafov.modular_configuration"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}