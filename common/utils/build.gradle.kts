plugins {
    alias(libs.plugins.baseproject.android.library)
    alias(libs.plugins.baseproject.android.library.compose)
}

android {
    namespace = "com.taghiashrafov.utils"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime.android)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}