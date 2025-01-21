plugins {
    alias(libs.plugins.baseproject.android.library)
    alias(libs.plugins.baseproject.android.library.compose)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.taghiashrafov.login"
}

dependencies {
    implementation(projects.common.modularConfiguration)
    implementation(projects.common.components)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
}

kapt {
    correctErrorTypes = true
}