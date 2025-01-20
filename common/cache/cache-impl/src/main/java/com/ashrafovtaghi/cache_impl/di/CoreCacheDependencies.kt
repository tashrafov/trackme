package com.ashrafovtaghi.cache_impl.di

import android.content.Context
import com.taghiashrafov.modular_configuration.BaseDependencies

interface CoreCacheDependencies : BaseDependencies {
    val context: Context
    val storageName: String
}