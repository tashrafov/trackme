package com.ashrafovtaghi.retrofit.di

import com.ashrafovtaghi.retrofit.NetworkApi
import com.taghiashrafov.modular_configuration.ComponentHolder

object NetworkComponentHolder : ComponentHolder<NetworkApi, NetworkDependencies> {
    @Volatile
    private var networkComponent: NetworkComponent? = null
    override fun init(dependencies: NetworkDependencies) {
        if (networkComponent == null) {
            synchronized(NetworkComponentHolder::class.java) {
                if (networkComponent == null) {
                    networkComponent = NetworkComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): NetworkApi {
        checkNotNull(networkComponent) { "NetworkComponent was not initialized!" }
        return networkComponent!!
    }

    override fun reset() {
        networkComponent = null
    }
}