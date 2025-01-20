plugins {
    alias(libs.plugins.baseproject.android.library)
    id("kotlin-kapt")
}

android {
    namespace = "com.taghiashrafov.cache_impl"
}

dependencies {
    implementation(projects.common.modularConfiguration)
    implementation(projects.common.cache.cacheApi)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //dataStore
    implementation(libs.androidx.datastore.preferences)

    //gson
    implementation(libs.google.gson)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}