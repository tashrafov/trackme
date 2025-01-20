package com.ashrafovtaghi.retrofit.di

import com.ashrafovtaghi.retrofit.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [NetworkDependencies::class],
    modules = [NetworkModule::class]
)
@Singleton
interface NetworkComponent : NetworkApi {
    companion object {
        fun initAndGet(dependencies: NetworkDependencies): NetworkComponent {
            return DaggerNetworkComponent
                .factory()
                .create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: NetworkDependencies): NetworkComponent
    }
}