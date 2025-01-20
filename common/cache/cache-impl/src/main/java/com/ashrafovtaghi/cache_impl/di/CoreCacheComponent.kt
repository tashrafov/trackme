package com.ashrafovtaghi.cache_impl.di

import com.ashrafovtaghi.cache_impl.CacheApi
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [CoreCacheModule::class],
    dependencies = [CoreCacheDependencies::class]
)
@Singleton
interface CoreCacheComponent : CacheApi {
    companion object {
        fun initAndGet(dependencies: CoreCacheDependencies): CoreCacheComponent {
            return DaggerCoreCacheComponent
                .factory()
                .create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: CoreCacheDependencies): CoreCacheComponent
    }
}