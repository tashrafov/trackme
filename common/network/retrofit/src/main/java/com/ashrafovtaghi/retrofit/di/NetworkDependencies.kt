package com.ashrafovtaghi.retrofit.di

import android.content.Context
import com.taghiashrafov.modular_configuration.BaseDependencies
import okhttp3.Interceptor

interface NetworkDependencies : BaseDependencies {
    val context: Context
    val baseUrl: String
    val networkInterceptors: List<Interceptor>
}