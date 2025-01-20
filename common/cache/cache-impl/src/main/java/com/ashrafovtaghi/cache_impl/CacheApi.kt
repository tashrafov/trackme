package com.ashrafovtaghi.cache_impl

import com.ashrafovtaghi.cache_api.DataStore
import com.ashrafovtaghi.cache_api.PrefUtils
import com.taghiashrafov.modular_configuration.BaseAPI

interface CacheApi : BaseAPI {
    fun getDataStore(): DataStore
    fun getPrefUtils(): PrefUtils
}