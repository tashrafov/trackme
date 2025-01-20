package com.ashrafovtaghi.cache_impl.di

import com.ashrafovtaghi.cache_impl.CacheApi
import com.taghiashrafov.modular_configuration.ComponentHolder

object CoreCacheComponentHolder : ComponentHolder<CacheApi, CoreCacheDependencies> {
    @Volatile
    private var coreCahceComponent: CoreCacheComponent? = null
    override fun init(dependencies: CoreCacheDependencies) {
        if (coreCahceComponent == null) {
            synchronized(CoreCacheComponentHolder::class.java) {
                if (coreCahceComponent == null) {
                    coreCahceComponent = CoreCacheComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): CacheApi {
        checkNotNull(coreCahceComponent) { "CacheComponent was not initialized!" }
        return coreCahceComponent!!
    }

    override fun reset() {
        coreCahceComponent = null
    }
}