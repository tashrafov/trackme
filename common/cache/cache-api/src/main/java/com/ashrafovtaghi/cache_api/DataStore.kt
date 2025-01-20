package com.ashrafovtaghi.cache_api

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow


abstract class DataStore(open val dataStoreName: String) {
    abstract fun remove(key: Preferences.Key<*>)
    abstract fun clear()
    abstract fun contains(key: Preferences.Key<*>): Flow<Boolean>
    abstract fun getString(key: Preferences.Key<String>, defaultValue: String = ""): Flow<String>
    abstract fun putString(key: Preferences.Key<String>, value: String)
    abstract fun getInt(key: Preferences.Key<Int>, defaultValue: Int = 0): Flow<Int>
    abstract fun putInt(key: Preferences.Key<Int>, value: Int)
    abstract fun getBoolean(
        key: Preferences.Key<Boolean>,
        defaultValue: Boolean = false
    ): Flow<Boolean>

    abstract fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    abstract fun getLong(key: Preferences.Key<Long>, defaultValue: Long = 0): Flow<Long>
    abstract fun putLong(key: Preferences.Key<Long>, value: Long)
    abstract fun getFloat(key: Preferences.Key<Float>, defaultValue: Float = 0f): Flow<Float>
    abstract fun putFloat(key: Preferences.Key<Float>, value: Float)
    abstract fun getDouble(key: Preferences.Key<Double>, defaultValue: Double = 0.0): Flow<Double>
    abstract fun putDouble(key: Preferences.Key<Double>, value: Double)
}