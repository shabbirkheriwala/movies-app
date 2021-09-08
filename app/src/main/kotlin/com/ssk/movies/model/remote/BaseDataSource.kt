package com.ssk.movies.model.remote

import android.util.Log
import com.ssk.movies.util.Resource
import retrofit2.Response

/**
 * Base datasource
 */
abstract class BaseDataSource {

    /**
     * Get result
     *
     * @param T
     * @param call
     * @receiver
     * @return
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("ERROR", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

}