plugins {
    alias(libs.plugins.baseproject.android.library)
    id("kotlin-kapt")
}

android {
    namespace = "com.taghiashrafov.network_retrofit"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.common.modularConfiguration)

    implementation(libs.androidx.core.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}