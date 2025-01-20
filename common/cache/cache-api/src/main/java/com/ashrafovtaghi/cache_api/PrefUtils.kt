package com.ashrafovtaghi.cache_api

import android.os.Parcelable

/**
 * Apps shared-preferences manager.
 *
 */
abstract class PrefUtils(open val prefName: String) {
    abstract fun remove(key: String)
    abstract fun contain(key: String): Boolean
    abstract fun putString(key: String, data: String)
    abstract fun getString(key: String, defaultValue: String = ""): String
    abstract fun putInt(key: String, data: Int)
    abstract fun getInt(key: String, defaultValue: Int = 0): Int
    abstract fun putLong(key: String, data: Long)
    abstract fun getLong(key: String, defaultValue: Long = 0): Long
    abstract fun putFloat(key: String, data: Float)
    abstract fun getFloat(key: String, defaultValue: Float = 0f): Float
    abstract fun putDouble(key: String, data: Double)
    abstract fun getDouble(key: String, defaultValue: Double = 0.0): Double
    abstract fun putBoolean(key: String, data: Boolean)
    abstract fun getBoolean(key: String, defaultValue: Boolean): Boolean
    abstract fun <T : Parcelable> putParcelable(key: String, data: T)
    abstract fun <T : Parcelable> getParcelable(key: String, type: Class<T>): T?
}