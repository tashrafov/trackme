package com.ashrafovtaghi.cache_impl

import android.content.Context
import android.os.Parcelable
import com.ashrafovtaghi.cache_api.PrefUtils
import com.google.gson.Gson
import javax.inject.Inject

internal class PrefUtilsImpl @Inject constructor(
    private val context: Context,
    private val gson: Gson,
    override val prefName: String
) : PrefUtils(prefName = prefName) {

    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    override fun contain(key: String): Boolean {
        return prefs.contains(key)
    }

    override fun putString(key: String, data: String) {
        prefs.edit().putString(key, data).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun putInt(key: String, data: Int) {
        prefs.edit().putInt(key, data).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    override fun putLong(key: String, data: Long) {
        prefs.edit().putLong(key, data).apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return prefs.getLong(key, defaultValue)
    }

    override fun putFloat(key: String, data: Float) {
        prefs.edit().putFloat(key, data).apply()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return prefs.getFloat(key, defaultValue)
    }

    override fun putDouble(key: String, data: Double) {
        prefs.edit().putString(key, data.toString()).apply()
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return prefs.getString(key, defaultValue.toString())?.toDouble() ?: defaultValue
    }

    override fun putBoolean(key: String, data: Boolean) {
        prefs.edit().putBoolean(key, data).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    override fun <T : Parcelable> putParcelable(key: String, data: T) {
        val json = gson.toJson(data)
        putString(key, json)
    }

    override fun <T : Parcelable> getParcelable(key: String, type: Class<T>): T? {
        val json = getString(key, "")
        return try {
            if (json.isNotEmpty()) {
                gson.fromJson(json, type)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}