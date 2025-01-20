package com.ashrafovtaghi.retrofit

import com.taghiashrafov.modular_configuration.BaseAPI
import retrofit2.Retrofit

interface NetworkApi : BaseAPI {
    fun getRetrofit(): Retrofit
}