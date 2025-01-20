plugins {
    alias(libs.plugins.baseproject.android.library)
    id("kotlin-kapt")
}

android {
    namespace = "com.taghiashrafov.cache_api"
}

dependencies {
    implementation(projects.common.modularConfiguration)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //dataStore
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}