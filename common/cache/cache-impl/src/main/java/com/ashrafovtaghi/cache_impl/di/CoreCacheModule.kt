package com.ashrafovtaghi.cache_impl.di

import android.content.Context
import com.ashrafovtaghi.cache_api.DataStore
import com.ashrafovtaghi.cache_api.PrefUtils
import com.ashrafovtaghi.cache_impl.DataStoreImpl
import com.ashrafovtaghi.cache_impl.PrefUtilsImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreCacheModule {
    @Singleton
    @Provides
    fun provideDataStore(context: Context, storageName: String): DataStore {
        return DataStoreImpl(context, storageName.plus(DATA_STORE_SUFFIX))
    }

    @Singleton
    @Provides
    fun providePrefUtils(context: Context, gson: Gson, prefName: String): PrefUtils {
        return PrefUtilsImpl(context, gson, prefName.plus(PREF_SUFFIX))
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    companion object {
        const val PREF_SUFFIX = "_preferences"
        const val DATA_STORE_SUFFIX = "_data_store"
    }
}