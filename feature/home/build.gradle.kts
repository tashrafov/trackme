plugins {
    alias(libs.plugins.baseproject.android.library)
    alias(libs.plugins.baseproject.android.library.compose)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.taghiashrafov.home"
}

dependencies {
    implementation(projects.common.modularConfiguration)
    implementation(projects.common.components)
    implementation(projects.common.utils)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widgets)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.accompanist.permissions)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
}

kapt {
    correctErrorTypes = true
}