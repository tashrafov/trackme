package com.ashrafovtaghi.cache_impl

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ashrafovtaghi.cache_api.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DataStoreImpl @Inject constructor(
    private val context: Context,
    override val dataStoreName: String
) : DataStore(dataStoreName = dataStoreName) {

    private val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
        name = dataStoreName
    )
    private val store = context.dataStore

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun remove(key: Preferences.Key<*>) {
        scope.launch {
            store.edit { it.remove(key) }
        }
    }

    override fun clear() {
        scope.launch {
            store.edit { it.clear() }
        }
    }

    override fun contains(key: Preferences.Key<*>): Flow<Boolean> {
        return store.data.map {
            it.contains(key)
        }
    }

    override fun getString(key: Preferences.Key<String>, defaultValue: String): Flow<String> =
        store.data.map {
            it[key] ?: defaultValue
        }

    override fun putString(key: Preferences.Key<String>, value: String) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }

    override fun getInt(key: Preferences.Key<Int>, defaultValue: Int): Flow<Int> {
        return store.data.map {
            it[key] ?: defaultValue
        }
    }

    override fun putInt(key: Preferences.Key<Int>, value: Int) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }

    override fun getBoolean(key: Preferences.Key<Boolean>, defaultValue: Boolean): Flow<Boolean> {
        return store.data.map {
            it[key] ?: defaultValue
        }
    }

    override fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }

    override fun getLong(key: Preferences.Key<Long>, defaultValue: Long): Flow<Long> {
        return store.data.map {
            it[key] ?: defaultValue
        }
    }

    override fun putLong(key: Preferences.Key<Long>, value: Long) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }

    override fun getFloat(key: Preferences.Key<Float>, defaultValue: Float): Flow<Float> {
        return store.data.map {
            it[key] ?: defaultValue
        }
    }

    override fun putFloat(key: Preferences.Key<Float>, value: Float) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }

    override fun getDouble(key: Preferences.Key<Double>, defaultValue: Double): Flow<Double> {
        return store.data.map {
            it[key] ?: defaultValue
        }
    }

    override fun putDouble(key: Preferences.Key<Double>, value: Double) {
        scope.launch {
            store.edit { it[key] = value }
        }
    }
}