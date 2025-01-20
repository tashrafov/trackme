package com.ashrafovtaghi.retrofit.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> Response<T>
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                Result.success(responseBody)
            } else {
                Result.failure(Throwable(response.message()))
            }
        } catch (throwable: Throwable) {
            Result.failure<T>(throwable)
        }
    }
}